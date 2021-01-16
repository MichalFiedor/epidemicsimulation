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
        return initialSimulationDataRepository.findInitialDataById(id);
    }

    public InitialSimulationData save(InitialSimulationData initialSimulationData) {
        return initialSimulationDataRepository.save(initialSimulationData);
    }

    public List<InitialSimulationData> findAll() {
        return initialSimulationDataRepository.findAllNotDeleted();
    }

    public InitialSimulationData edit(InitialSimulationData initialSimulationData) throws Exception {
        InitialSimulationData initialSimulationDataFromDataBase = initialSimulationDataRepository
                .findById(initialSimulationData.getId()).orElseThrow(()->new Exception("Empty Initial Simulation Data"));
        List<SingleDaySimulation> simulations = initialSimulationDataFromDataBase.getSingleDaySimulations();
        setSimulationsAsDeleted(simulations);
        List<SingleDaySimulation> simulationsBasedOnNewData = singleDaySimulationCalculationService
                .calculateEverySimulationDay(initialSimulationData);
        initialSimulationData.setSingleDaySimulations(simulationsBasedOnNewData);
        return initialSimulationDataRepository.save(initialSimulationData);
    }

    public void deleteById(long id) {
        InitialSimulationData initialSimulationData = initialSimulationDataRepository.findById(id).orElseThrow();
        initialSimulationData.setDeleted(true);
        List<SingleDaySimulation> simulations = initialSimulationData.getSingleDaySimulations();
        setSimulationsAsDeleted(simulations);
        initialSimulationDataRepository.save(initialSimulationData);
    }

    public InitialSimulationData addInitialDataAndGenerateSimulation(InitialSimulationData initialSimulationData) {
        initialSimulationDataRepository.save(initialSimulationData);
        List<SingleDaySimulation> singleDaySimulations = singleDaySimulationCalculationService
                .calculateEverySimulationDay(initialSimulationData);
        singleDaySimulations.forEach(singleDaySimulation -> singleDaySimulationRepository.save(singleDaySimulation));
        initialSimulationData.setSingleDaySimulations(singleDaySimulations);
        return initialSimulationDataRepository.save(initialSimulationData);
    }

    private void setSimulationsAsDeleted(List<SingleDaySimulation> simulations){
        for(SingleDaySimulation singleDaySimulation : simulations){
            singleDaySimulation.setDeleted(true);
            singleDaySimulationRepository.save(singleDaySimulation);
        }
    }
}
