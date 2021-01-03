package com.fiedormichal.epidemicsimulation.service.recoveredCounterService;

import com.fiedormichal.epidemicsimulation.model.CalculationData;
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
                                      CalculationData calculationData) {
        long lastRecordId= singleDaySimulationRepository.findFirstByOrderByIdDesc().getId()+1;
        SingleDaySimulation simulationDayFromCurrentSimulationDayMinusPeriodBetweenInfectionAndRecovery =
                singleDaySimulationRepository.findById(lastRecordId - calculationData.getDaysFromInfectionToRecovery() + 1).orElseThrow();
        long numberOfInfectedPeople = simulationDayFromCurrentSimulationDayMinusPeriodBetweenInfectionAndRecovery.getNumberOfInfectedPeople();
        long totalRecoveredPeople = numberOfInfectedPeople - Math.round(numberOfInfectedPeople * calculationData.getMortalityRate());
        currentSimulationDay.setNumberOfPeopleWhoRecoveredAndGainedImmunity(totalRecoveredPeople);

    }
}
