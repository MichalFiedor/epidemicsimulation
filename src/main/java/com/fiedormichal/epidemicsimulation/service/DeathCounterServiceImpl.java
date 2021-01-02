package com.fiedormichal.epidemicsimulation.service;

import com.fiedormichal.epidemicsimulation.model.SingleDaySimulation;
import com.fiedormichal.epidemicsimulation.repository.SingleDaySimulationRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class DeathCounterServiceImpl implements DeathsCounterService {
    private final SingleDaySimulationRepository singleDaySimulationRepository;

    @Override
    public void countDeathPeople(SingleDaySimulation currentSimulationDay,
                                 double mortalityRate, int daysFromInfectionToDeath, long previousDay, List<SingleDaySimulation> singleDaySimulations) {
        long lastRecordId= singleDaySimulationRepository.findFirstByOrderByIdDesc().getId()+1;
        SingleDaySimulation simulationDayFromCurrentSimulationDayMinusPeriodBetweenInfectionAndDeath =
                singleDaySimulationRepository.findById(lastRecordId - daysFromInfectionToDeath + 1).orElseThrow();
        long numberOfDeathPeopleForCurrentSimulationDay = Math.round(
                simulationDayFromCurrentSimulationDayMinusPeriodBetweenInfectionAndDeath.getNumberOfInfectedPeople() * mortalityRate);
        currentSimulationDay.setNumberOfDeathPeople(numberOfDeathPeopleForCurrentSimulationDay);
    }
}

