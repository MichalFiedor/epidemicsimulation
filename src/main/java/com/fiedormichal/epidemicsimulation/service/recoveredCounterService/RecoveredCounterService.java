package com.fiedormichal.epidemicsimulation.service.recoveredCounterService;

import com.fiedormichal.epidemicsimulation.model.CalculationData;
import com.fiedormichal.epidemicsimulation.model.SingleDaySimulation;

public interface RecoveredCounterService {
    void countCurrentRecovered(SingleDaySimulation currentSimulationDay, CalculationData calculationData, int iterator);
}
