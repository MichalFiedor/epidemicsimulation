package com.fiedormichal.epidemicsimulation.service;

import com.fiedormichal.epidemicsimulation.model.InitialSimulationData;

import java.util.List;

public interface InitialSimulationDataService {
    InitialSimulationData save(InitialSimulationData initialSimulationData);
    InitialSimulationData findById(long id);
    List<InitialSimulationData> findAll();
    InitialSimulationData edit(InitialSimulationData initialSimulationData);
    void deleteById(long id);
}
