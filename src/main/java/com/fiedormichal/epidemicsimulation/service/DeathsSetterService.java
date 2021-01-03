package com.fiedormichal.epidemicsimulation.service;

import com.fiedormichal.epidemicsimulation.model.SingleDaySimulation;

public interface DeathsSetterService {
    void setTotalNumberOfDeathsForSingleSimulationDay(SingleDaySimulation singleDaySimulation, int daysFromInfectionToDeath,
                                                long numberOfDaysWhenOccursDeath, long counterFromStartOfTheSimulationToMaxValueOfInfectedPeople,
                                                double mortalityRate, long maxNumberOfDeathPeople, long iterator);
}
