package com.fiedormichal.epidemicsimulation.service.infectedPeopleCounterService;

import com.fiedormichal.epidemicsimulation.model.CalculationData;
import com.fiedormichal.epidemicsimulation.model.SingleDaySimulation;

public interface InfectedPeopleCounterService {
    void countInfectedPeopleWhenParamIsLowerThanNumberOfPopulation(SingleDaySimulation currentSimulationDay, CalculationData calculationData, int iterator);
     void countInfectedPeopleWhenParamExceedNumberOfPopulation(SingleDaySimulation currentSimulationDay, CalculationData calculationData);
     void countInfectedPeopleWhenParamReachedMaxValueForSimulation(SingleDaySimulation currentSimulationDay, CalculationData calculationData);
}
