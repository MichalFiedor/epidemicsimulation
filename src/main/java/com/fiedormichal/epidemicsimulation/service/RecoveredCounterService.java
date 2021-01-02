package com.fiedormichal.epidemicsimulation.service;

import com.fiedormichal.epidemicsimulation.model.SingleDaySimulation;

public interface RecoveredCounterService {
    void countCurrentRecovered(
            SingleDaySimulation currentSimulationDay,
            long daysFromInfectionToRecovery, long previousDay, double mortalityRate);
}
