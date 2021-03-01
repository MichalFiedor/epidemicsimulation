package com.fiedormichal.epidemicsimulation.service;

import com.fiedormichal.epidemicsimulation.exception.InitialDataNotFoundException;
import com.fiedormichal.epidemicsimulation.model.InitialSimulationData;
import com.fiedormichal.epidemicsimulation.model.SingleDaySimulation;
import com.fiedormichal.epidemicsimulation.repository.InitialSimulationDataRepository;
import com.fiedormichal.epidemicsimulation.repository.SingleDaySimulationRepository;
import com.fiedormichal.epidemicsimulation.service.singleDaySimulationCalculationService.SingleDaySimulationCalculationService;
import javassist.NotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@RequiredArgsConstructor
@Service
public class InitialSimulationDataCrudService {
    private final InitialSimulationDataRepository initialSimulationDataRepository;
    private final SingleDaySimulationRepository singleDaySimulationRepository;
    private final SingleDaySimulationCalculationService singleDaySimulationCalculationService;

    public InitialSimulationData findById(long id) {
        InitialSimulationData initialSimulationData = initialSimulationDataRepository.findById(id).orElseThrow(()->
                new InitialDataNotFoundException("Initial data with id: " + id + " does not exist."));
        return initialSimulationData;
    }

    public List<InitialSimulationData> findAll() {
        return initialSimulationDataRepository.findAll();
    }

    public InitialSimulationData edit(InitialSimulationData initialSimulationData) {
        InitialSimulationData initialSimulationDataFromDataBase = initialSimulationDataRepository
                .findById(initialSimulationData.getId()).orElseThrow(()->
                        new InitialDataNotFoundException("Initial data with id: " + initialSimulationData.getId() + " does not exist."));
        List<SingleDaySimulation> simulations = initialSimulationDataFromDataBase.getSingleDaySimulations();
        deleteOutOfDateSimulations(simulations);
        List<SingleDaySimulation> simulationsBasedOnNewData = singleDaySimulationCalculationService
                .calculateEverySimulationDay(initialSimulationData);

        simulationsBasedOnNewData.forEach(singleDaySimulation -> singleDaySimulationRepository.save(singleDaySimulation));
        initialSimulationData.setSingleDaySimulations(simulationsBasedOnNewData);
        return initialSimulationDataRepository.save(initialSimulationData);
    }

    public void deleteById(long id) {
        initialSimulationDataRepository.deleteById(id);
    }

    public InitialSimulationData addInitialDataAndGenerateSimulation(InitialSimulationData initialSimulationData) {
        initialSimulationDataRepository.save(initialSimulationData);
        List<SingleDaySimulation> singleDaySimulations = singleDaySimulationCalculationService
                .calculateEverySimulationDay(initialSimulationData);
        singleDaySimulations.forEach(singleDaySimulation -> singleDaySimulationRepository.save(singleDaySimulation));
        initialSimulationData.setSingleDaySimulations(singleDaySimulations);
        return initialSimulationDataRepository.save(initialSimulationData);
    }

    private void deleteOutOfDateSimulations(List<SingleDaySimulation> simulations){
        for(SingleDaySimulation singleDaySimulation : simulations){
            singleDaySimulationRepository.delete(singleDaySimulation);
        }
    }
}
