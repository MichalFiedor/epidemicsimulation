package com.fiedormichal.epidemicsimulation.service.recoveredCounterService;

import com.fiedormichal.epidemicsimulation.model.CalculationData;
import com.fiedormichal.epidemicsimulation.model.SingleDaySimulation;
import com.fiedormichal.epidemicsimulation.repository.SingleDaySimulationRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class RecoveredCounterServiceImpl implements RecoveredCounterService {

    @Override
    public void countCurrentRecovered(SingleDaySimulation currentSimulationDay,
                                      CalculationData calculationData, int iterator) {
        SingleDaySimulation simulationDayFromCurrentSimulationDayMinusPeriodBetweenInfectionAndRecovery =
                calculationData.getSingleDaySimulationsListForInitialData().get(iterator - calculationData.getDaysFromInfectionToRecovery());
        long numberOfInfectedPeople = simulationDayFromCurrentSimulationDayMinusPeriodBetweenInfectionAndRecovery.getNumberOfInfectedPeople();
        long totalRecoveredPeopleForCurrentDay = numberOfInfectedPeople - Math.round(numberOfInfectedPeople * calculationData.getMortalityRate());
        currentSimulationDay.setNumberOfPeopleWhoRecoveredAndGainedImmunity(totalRecoveredPeopleForCurrentDay);
    }
}
