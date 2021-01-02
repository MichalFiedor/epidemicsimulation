package com.fiedormichal.epidemicsimulation.service;

import com.fiedormichal.epidemicsimulation.model.SingleDaySimulation;

public interface InfectedPeopleCounterService {
    void countInfectedPeopleBeforeParamReachedNumberOfPopulation(SingleDaySimulation currentSimulationDay,
                                                                 double howManyPeopleWillBeInfectedByOnePerson, long previousDay, double mortalityRate,
                                                                 long daysFromInfectionToRecovery);
     void countInfectedPeopleWhenParamExceedNumberOfPopulation(SingleDaySimulation currentSimulationDay, long population);
}
