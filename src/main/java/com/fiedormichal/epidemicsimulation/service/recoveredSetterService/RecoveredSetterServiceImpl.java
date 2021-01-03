package com.fiedormichal.epidemicsimulation.service.recoveredSetterService;

import com.fiedormichal.epidemicsimulation.model.CalculationData;
import com.fiedormichal.epidemicsimulation.model.SingleDaySimulation;
import com.fiedormichal.epidemicsimulation.service.recoveredCounterService.RecoveredCounterService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class RecoveredSetterServiceImpl implements RecoveredSetterService {
    private final RecoveredCounterService recoveredCounterService;

    @Override
    public void setTotalNumberOfRecoveredForSingleSimulationDay(SingleDaySimulation singleDaySimulation, CalculationData calculationData, long i) {

        if (i < calculationData.getDaysFromInfectionToRecovery() || calculationData.getCounterFromStartOfTheSimulationToMaxValueOfInfectedPeople() +
                calculationData.getDaysFromInfectionToRecovery() < i) {
            singleDaySimulation.setNumberOfPeopleWhoRecoveredAndGainedImmunity(0);
        } else {
            if (calculationData.isShouldSetZeroForNumberRecoveredPeople()) {
                singleDaySimulation.setNumberOfPeopleWhoRecoveredAndGainedImmunity(0);
            } else {
                try {
                    recoveredCounterService.countCurrentRecovered(singleDaySimulation, calculationData);
                } catch (Exception exception) {
                    exception.printStackTrace();
                }
            }
        }
    }
}
