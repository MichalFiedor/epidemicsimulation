package com.fiedormichal.epidemicsimulation.service.deathsCounterService;

import com.fiedormichal.epidemicsimulation.model.CalculationData;
import com.fiedormichal.epidemicsimulation.model.SingleDaySimulation;

public interface DeathsCounterService {
    void countDeathPeople(SingleDaySimulation currentSimulationDay, CalculationData calculationData);
}
