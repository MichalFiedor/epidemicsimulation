package com.fiedormichal.epidemicsimulation.service;

import com.fiedormichal.epidemicsimulation.model.SingleDaySimulation;
import org.springframework.stereotype.Service;

public interface DeathsCounterService {
    void countDeathPeople(SingleDaySimulation currentSimulationDay,
                          double mortalityRate, int daysFromInfectionToDeath, long previousDay);
}
