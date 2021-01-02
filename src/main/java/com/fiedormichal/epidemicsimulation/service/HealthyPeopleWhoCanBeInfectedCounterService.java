package com.fiedormichal.epidemicsimulation.service;

import com.fiedormichal.epidemicsimulation.model.SingleDaySimulation;

public interface HealthyPeopleWhoCanBeInfectedCounterService {
    void countHealthyPeopleWhoCanBeInfected(SingleDaySimulation currentSimulationDay, long population);
}
