package com.fiedormichal.epidemicsimulation.service;

import com.fiedormichal.epidemicsimulation.model.InitialSimulationData;
import com.fiedormichal.epidemicsimulation.model.SingleDaySimulation;

import java.util.List;

public interface InitialSimulationDataService {
    InitialSimulationData findById(long id);
    InitialSimulationData save(InitialSimulationData initialSimulationData);
    List<InitialSimulationData> findAll();
    InitialSimulationData edit(InitialSimulationData initialSimulationData);
    void deleteById(long id);
    InitialSimulationData addSimulations(List<SingleDaySimulation> singleDaySimulations,
                                                  InitialSimulationData initialSimulationData);
}
