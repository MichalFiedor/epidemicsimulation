package com.fiedormichal.epidemicsimulation.service.infectedPeopleCounterService;

import com.fiedormichal.epidemicsimulation.model.CalculationData;
import com.fiedormichal.epidemicsimulation.model.SingleDaySimulation;

public interface InfectedPeopleCounterService {
    void countInfectedPeopleBeforeParamReachedNumberOfPopulation(SingleDaySimulation currentSimulationDay, CalculationData calculationData, long iterator) throws Exception;
     void countInfectedPeopleWhenParamExceedNumberOfPopulation(SingleDaySimulation currentSimulationDay, CalculationData calculationData);
     void countInfectedPeopleWhenParamReachedMaxValueForSimulation(SingleDaySimulation currentSimulationDay, CalculationData calculationData);
}
