package com.fiedormichal.epidemicsimulation.service.deathsCounterService;

import com.fiedormichal.epidemicsimulation.model.CalculationData;
import com.fiedormichal.epidemicsimulation.model.SingleDaySimulation;
import com.fiedormichal.epidemicsimulation.repository.SingleDaySimulationRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class DeathCounterServiceImpl implements DeathsCounterService {

    @Override
    public void countDeathPeople(SingleDaySimulation currentSimulationDay,
                                                CalculationData calculationData, int iterator)  {
        SingleDaySimulation simulationDayFromCurrentSimulationDayMinusPeriodBetweenInfectionAndDeath =
                calculationData.getSingleDaySimulationsListForInitialData().
                        get(iterator - calculationData.getDaysFromInfectionToDeath());
        long numberOfDeathPeopleForCurrentSimulationDay = Math.round(
                simulationDayFromCurrentSimulationDayMinusPeriodBetweenInfectionAndDeath.getNumberOfInfectedPeople() * calculationData.getMortalityRate());
        currentSimulationDay.setNumberOfDeathPeople(numberOfDeathPeopleForCurrentSimulationDay);
    }
}

