package com.fiedormichal.epidemicsimulation.service.healthyPeopleWhoCanBeInfectedSetterService;

import com.fiedormichal.epidemicsimulation.model.CalculationData;
import com.fiedormichal.epidemicsimulation.model.SingleDaySimulation;

public interface HealthyPeopleWhoCanBeInfectedSetterService {
    void setTotalNumberOfHealthyPeopleWhoCanBeInfectedForSingleSimulationDay(SingleDaySimulation singleDaySimulation,
                                                                             CalculationData calculationData);
}
