package com.fiedormichal.epidemicsimulation.service;

import com.fiedormichal.epidemicsimulation.model.CalculationData;
import com.fiedormichal.epidemicsimulation.model.SingleDaySimulation;
import com.fiedormichal.epidemicsimulation.service.recoveredCounterService.RecoveredCounterService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class RecoveredSetterService {
    private final RecoveredCounterService recoveredCounterService;

    public void setTotalNumberOfRecoveredForSingleSimulationDay(SingleDaySimulation currentSimulationDay, CalculationData calculationData, int i) {

        if (i < calculationData.getDaysFromInfectionToRecovery() || calculationData.getCounterFromStartOfTheSimulationToOccursMaxValueOfInfectedPeopleForSimulation() +
                calculationData.getDaysFromInfectionToRecovery() < i) {
            currentSimulationDay.setNumberOfPeopleWhoRecoveredAndGainedImmunity(0);
        } else {
            if (calculationData.isShouldSetZeroForNumberOfRecoveredPeople()) {
                currentSimulationDay.setNumberOfPeopleWhoRecoveredAndGainedImmunity(0);
            } else {
                try {
                    recoveredCounterService.countCurrentRecovered(currentSimulationDay, calculationData, i);
                } catch (Exception exception) {
                    exception.printStackTrace();
                }
            }
        }
    }
}
