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
                                      CalculationData calculationData) throws Exception {
        long currentDayId= singleDaySimulationRepository.findFirstByOrderByIdDesc().getId()+1;
        SingleDaySimulation simulationDayFromCurrentSimulationDayMinusPeriodBetweenInfectionAndRecovery =
                singleDaySimulationRepository.findById(currentDayId - calculationData.getDaysFromInfectionToRecovery() + 1).orElseThrow(()->new Exception("Empty Simulation Day"));
        long numberOfInfectedPeople = simulationDayFromCurrentSimulationDayMinusPeriodBetweenInfectionAndRecovery.getNumberOfInfectedPeople();
        long totalRecoveredPeopleForCurrentDay = numberOfInfectedPeople - Math.round(numberOfInfectedPeople * calculationData.getMortalityRate());
        currentSimulationDay.setNumberOfPeopleWhoRecoveredAndGainedImmunity(totalRecoveredPeopleForCurrentDay);
    }
}
