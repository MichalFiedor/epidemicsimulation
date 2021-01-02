package com.fiedormichal.epidemicsimulation.service;

import com.fiedormichal.epidemicsimulation.model.SingleDaySimulation;

public interface InfectedPeopleSetterService {
    void changeMethodForCountingAsNeededAndSetValue(SingleDaySimulation singleDaySimulation, long population,
                                                                                               long maxValueOfInfectedPeople, double peopleInfectedByOnePerson,
                                                                                               double mortalityRate, int daysFromInfectionToRecovery, long iterator,
                                                                                               long counterFromStartOfTheSimulationToMaxValueOfInfectedPeople,
                                                                                               boolean shouldChangeMethodForCountingNumberOfInfectedPeopleWhenMaxValueOccurs,
                                                                                               int shouldChangeMethodForCountingNumberOfInfectedPeople);

    void setZero(SingleDaySimulation singleDaySimulation, int shouldSetZeroForNumberInfectedPeople);
}