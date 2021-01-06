package com.fiedormichal.epidemicsimulation.service.deathsCounterService;

import com.fiedormichal.epidemicsimulation.model.CalculationData;
import com.fiedormichal.epidemicsimulation.model.SingleDaySimulation;
import com.fiedormichal.epidemicsimulation.repository.SingleDaySimulationRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class DeathCounterServiceImpl implements DeathsCounterService {
    private final SingleDaySimulationRepository singleDaySimulationRepository;

    @Override
    public void countDeathPeople(SingleDaySimulation currentSimulationDay,
                                                CalculationData calculationData)  {
        long lastRecordId= singleDaySimulationRepository.findFirstByOrderByIdDesc().getId()+1;
        SingleDaySimulation simulationDayFromCurrentSimulationDayMinusPeriodBetweenInfectionAndDeath =
                null;
        try {
            simulationDayFromCurrentSimulationDayMinusPeriodBetweenInfectionAndDeath = singleDaySimulationRepository.findById(lastRecordId
                    - calculationData.getDaysFromInfectionToDeath() + 1).orElseThrow(()->new Exception("Empty Simulation Day"));
        } catch (Exception exception) {
            exception.printStackTrace();
        }
        long numberOfDeathPeopleForCurrentSimulationDay = Math.round(
                simulationDayFromCurrentSimulationDayMinusPeriodBetweenInfectionAndDeath.getNumberOfInfectedPeople() * calculationData.getMortalityRate());
        currentSimulationDay.setNumberOfDeathPeople(numberOfDeathPeopleForCurrentSimulationDay);
    }
}

