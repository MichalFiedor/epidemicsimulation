package com.fiedormichal.epidemicsimulation.service;

import com.fiedormichal.epidemicsimulation.model.SingleDaySimulation;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class DeathsSetterServiceImpl implements DeathsSetterService {
    private final DeathsCounterService deathsCounterService;

    @Override
    public void setTotalNumberOfDeathsForSingleSimulationDay(SingleDaySimulation singleDaySimulation, int daysFromInfectionToDeath, long numberOfDaysWhenAmountOfInfectedPeopleGrows,
                                                             long counterFromStartOfTheSimulationToMaxValueOfInfectedPeople, double mortalityRate, long maxNumberOfDeathPeople, long i,
                                                             List<SingleDaySimulation> singleDaySimulations) {

        if (i < daysFromInfectionToDeath || (i > numberOfDaysWhenAmountOfInfectedPeopleGrows + daysFromInfectionToDeath &&
                numberOfDaysWhenAmountOfInfectedPeopleGrows != 0) || counterFromStartOfTheSimulationToMaxValueOfInfectedPeople + daysFromInfectionToDeath < i) {
            singleDaySimulation.setNumberOfDeathPeople(0);
        } else {
            deathsCounterService.countDeathPeople(singleDaySimulation, mortalityRate, daysFromInfectionToDeath, i, singleDaySimulations);
            if (maxNumberOfDeathPeople < singleDaySimulation.getNumberOfDeathPeople()) {
                singleDaySimulation.setNumberOfDeathPeople(0);
            }
        }
    }
}
