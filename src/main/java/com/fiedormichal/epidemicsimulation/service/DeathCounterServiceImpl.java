package com.fiedormichal.epidemicsimulation.service;

import com.fiedormichal.epidemicsimulation.model.SingleDaySimulation;
import com.fiedormichal.epidemicsimulation.repository.SingleDaySimulationRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class DeathCounterServiceImpl implements DeathCounterService {
    private final SingleDaySimulationRepository singleDaySimulationRepository;

    @Override
    public void countDeathPeople(SingleDaySimulation currentSimulationDay,
                                 double mortalityRate, int daysFromInfectionToDeath, long previousDay) {
        SingleDaySimulation simulationDayFromCurrentSimulationDayMinusPeriodBetweenInfectionAndDeath =
                singleDaySimulationRepository.findById(previousDay - daysFromInfectionToDeath + 1).orElseThrow();
        long numberOfDeathPeopleForCurrentSimulationDay = Math.round(
                simulationDayFromCurrentSimulationDayMinusPeriodBetweenInfectionAndDeath.getNumberOfInfectedPeople() * mortalityRate);
        currentSimulationDay.setNumberOfDeathPeople(numberOfDeathPeopleForCurrentSimulationDay);
    }
}

