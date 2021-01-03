package com.fiedormichal.epidemicsimulation.service.initialSimulationDataService;

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
public class InitialSimulationDataServiceImpl implements InitialSimulationDataService{
    private final InitialSimulationDataRepository initialSimulationDataRepository;
    private final SingleDaySimulationRepository singleDaySimulationRepository;
    private final SingleDaySimulationCalculationService singleDaySimulationCalculationService;

    @Override
    public InitialSimulationData findById(long id) {
        return initialSimulationDataRepository.findById(id).orElseThrow();
    }

    @Override
    public InitialSimulationData save(InitialSimulationData initialSimulationData) {
        return initialSimulationDataRepository.save(initialSimulationData);
    }

    @Override
    public List<InitialSimulationData> findAll() {
        return initialSimulationDataRepository.findAllNotDeleted();
    }

    @Override
    public InitialSimulationData edit(InitialSimulationData initialSimulationData) {
        InitialSimulationData initialSimulationDataFromDataBase = initialSimulationDataRepository
                .findById(initialSimulationData.getId()).orElseThrow();
        List<SingleDaySimulation> simulations = initialSimulationDataFromDataBase.getSingleDaySimulations();
        setSimulationsAsDeleted(simulations);
        List<SingleDaySimulation> simulationsBasedOnNewData = singleDaySimulationCalculationService
                .calculateEverySimulationDay(initialSimulationData);
        initialSimulationData.setSingleDaySimulations(simulationsBasedOnNewData);
        return initialSimulationDataRepository.save(initialSimulationData);
    }

    @Override
    public void deleteById(long id) {
        InitialSimulationData initialSimulationData = initialSimulationDataRepository.findById(id).orElseThrow();
        initialSimulationData.setDeleted(true);
        List<SingleDaySimulation> simulations = initialSimulationData.getSingleDaySimulations();
        setSimulationsAsDeleted(simulations);
        initialSimulationDataRepository.save(initialSimulationData);
    }

    @Override
    public InitialSimulationData addSimulations(InitialSimulationData initialSimulationData) {
        List<SingleDaySimulation> singleDaySimulations = singleDaySimulationCalculationService
                .calculateEverySimulationDay(initialSimulationData);
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
