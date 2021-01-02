package com.fiedormichal.epidemicsimulation.service;

import com.fiedormichal.epidemicsimulation.model.SingleDaySimulation;

public interface RecoveredSetterService {
    void setTotalNumberOfRecoveredForSingleSimulationDay(SingleDaySimulation singleDaySimulation, int daysFromInfectionToRecovery,
                                                         long counterFromStartOfTheSimulationToMaxValueOfInfectedPeople, boolean shouldSetZeroForNumberRecoveredPeople,
                                                         double mortalityRate, long iterator);
}
