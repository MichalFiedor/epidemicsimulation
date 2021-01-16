package com.fiedormichal.epidemicsimulation.service;

import com.fiedormichal.epidemicsimulation.model.InitialSimulationData;
import com.fiedormichal.epidemicsimulation.model.SingleDaySimulation;
import com.fiedormichal.epidemicsimulation.repository.InitialSimulationDataRepository;
import com.fiedormichal.epidemicsimulation.repository.SingleDaySimulationRepository;
import com.fiedormichal.epidemicsimulation.service.singleDaySimulationCalculationService.SingleDaySimulationCalculationService;
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
        try{
            return initialSimulationDataRepository.findById(id).orElseThrow(()->new Exception("Empty Initial Simulation Data"));
        }catch (Exception e){
            e.printStackTrace();
        }
        return new InitialSimulationData();
    }

    public List<InitialSimulationData> findAll() {
        return initialSimulationDataRepository.findAll();
    }

    public InitialSimulationData edit(InitialSimulationData initialSimulationData) throws Exception {
        InitialSimulationData initialSimulationDataFromDataBase = initialSimulationDataRepository
                .findById(initialSimulationData.getId()).orElseThrow(()->new Exception("Empty Initial Simulation Data"));
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
