package com.fiedormichal.epidemicsimulation.service.healthyPeopleWhoCanBeInfectedService;

import com.fiedormichal.epidemicsimulation.model.CalculationData;
import com.fiedormichal.epidemicsimulation.model.SingleDaySimulation;

public interface HealthyPeopleWhoCanBeInfectedCounterService {
    void countHealthyPeopleWhoCanBeInfected(SingleDaySimulation currentSimulationDay, CalculationData calculationData);
}
