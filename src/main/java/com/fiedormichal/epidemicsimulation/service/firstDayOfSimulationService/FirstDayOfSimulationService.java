package com.fiedormichal.epidemicsimulation.service.firstDayOfSimulationService;

import com.fiedormichal.epidemicsimulation.model.InitialSimulationData;
import com.fiedormichal.epidemicsimulation.model.SingleDaySimulation;

public interface FirstDayOfSimulationService {
    SingleDaySimulation createFirstDayOfSimulation(InitialSimulationData initialSimulationData);
}
