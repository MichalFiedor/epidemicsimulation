package com.fiedormichal.epidemicsimulation.service.recoveredSetterService;

import com.fiedormichal.epidemicsimulation.model.CalculationData;
import com.fiedormichal.epidemicsimulation.model.SingleDaySimulation;

public interface RecoveredSetterService {
    void setTotalNumberOfRecoveredForSingleSimulationDay(SingleDaySimulation singleDaySimulation, CalculationData calculationData, long iterator);
}
