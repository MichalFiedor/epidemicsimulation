package com.fiedormichal.epidemicsimulation.service;

import com.fiedormichal.epidemicsimulation.model.SingleDaySimulation;

public interface HealthyPeopleWhoCanBeInfectedSetterService {
    void setTotalNumberOfHealthyPeopleWhoCanBeInfectedForSingleSimulationDay(SingleDaySimulation singleDaySimulation,
                                                                             boolean shouldSetZeroForNumberHealthPeopleWhoCanBeInfected,
                                                                             boolean setConstantValueOfPeopleWhoCanBeInfected, long minValueOfPeopleWhoCanBeInfected,
                                                                             long population);
}
