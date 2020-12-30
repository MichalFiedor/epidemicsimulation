package com.fiedormichal.epidemicsimulation.service;

import com.fiedormichal.epidemicsimulation.model.InitialSimulationData;
import com.fiedormichal.epidemicsimulation.model.SingleDaySimulation;

import java.util.List;

public interface SingleDaySimulationCalculationService {
    List<SingleDaySimulation> calculateEverySimulationDay(InitialSimulationData initialSimulationData);

}
