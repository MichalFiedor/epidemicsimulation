package com.fiedormichal.epidemicsimulation.service;

import com.fiedormichal.epidemicsimulation.model.SingleDaySimulation;
import com.fiedormichal.epidemicsimulation.repository.SingleDaySimulationRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class RecoveredCounterServiceImpl implements RecoveredCounterService {
    private final SingleDaySimulationRepository singleDaySimulationRepository;

    @Override
    public void countCurrentRecovered(SingleDaySimulation currentSimulationDay,
                                      long daysFromInfectionToRecovery, long previousDay, double mortalityRate) {
        SingleDaySimulation simulationDayFromCurrentSimulationDayMinusPeriodBetweenInfectionAndRecovery =
                singleDaySimulationRepository.findById(previousDay - daysFromInfectionToRecovery + 1).orElseThrow();
        long numberOfInfectedPeople = simulationDayFromCurrentSimulationDayMinusPeriodBetweenInfectionAndRecovery.getNumberOfInfectedPeople();
        long totalRecoveredPeople = numberOfInfectedPeople - Math.round(numberOfInfectedPeople * mortalityRate);
        currentSimulationDay.setNumberOfPeopleWhoRecoveredAndGainedImmunity(totalRecoveredPeople);

    }
}
