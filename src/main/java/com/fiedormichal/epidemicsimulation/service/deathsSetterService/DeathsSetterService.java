package com.fiedormichal.epidemicsimulation.service.deathsSetterService;

import com.fiedormichal.epidemicsimulation.model.CalculationData;
import com.fiedormichal.epidemicsimulation.model.SingleDaySimulation;

public interface DeathsSetterService {
    void setTotalNumberOfDeathsForSingleSimulationDay(SingleDaySimulation singleDaySimulation, CalculationData calculationData, long iterator);
}
