package com.fiedormichal.epidemicsimulation.service;

import com.fiedormichal.epidemicsimulation.model.SingleDaySimulation;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class RecoveredSetterServiceImpl implements RecoveredSetterService {
    private final RecoveredCounterService recoveredCounterService;

    @Override
    public void setTotalNumberOfRecoveredForSingleSimulationDay(SingleDaySimulation singleDaySimulation, int daysFromInfectionToRecovery,
                                                                long counterFromStartOfTheSimulationToMaxValueOfInfectedPeople,
                                                                boolean shouldSetZeroForNumberRecoveredPeople, double mortalityRate, long i) {

        if (i < daysFromInfectionToRecovery || counterFromStartOfTheSimulationToMaxValueOfInfectedPeople + daysFromInfectionToRecovery < i) {
            singleDaySimulation.setNumberOfPeopleWhoRecoveredAndGainedImmunity(0);
        } else {
            if (shouldSetZeroForNumberRecoveredPeople) {
                singleDaySimulation.setNumberOfPeopleWhoRecoveredAndGainedImmunity(0);
            } else {
                recoveredCounterService.countCurrentRecovered(
                        singleDaySimulation, daysFromInfectionToRecovery, i, mortalityRate);
            }
        }
    }
}
