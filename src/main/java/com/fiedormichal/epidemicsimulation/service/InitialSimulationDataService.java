package com.fiedormichal.epidemicsimulation.service;

import com.fiedormichal.epidemicsimulation.model.InitialSimulationData;

public interface InitialSimulationDataService {
    InitialSimulationData save(InitialSimulationData initialSimulationData);
    InitialSimulationData findById(long id);
    InitialSimulationData edit(InitialSimulationData initialSimulationData);
    void deleteById(long id);
}
