package com.fiedormichal.epidemicsimulation.service;

import com.fiedormichal.epidemicsimulation.model.CalculationData;
import com.fiedormichal.epidemicsimulation.model.SingleDaySimulation;
import com.fiedormichal.epidemicsimulation.service.deathsCounterService.DeathsCounterService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class DeathsSetterService {
    private final DeathsCounterService deathsCounterService;
    public void setTotalNumberOfDeathsForSingleSimulationDay(SingleDaySimulation currentSimulationDay, CalculationData calculationData, int iterator) {

        if (iterator < calculationData.getDaysFromInfectionToDeath() || (iterator > calculationData.getNumberOfDaysWhenAmountOfInfectedPeopleGrowsToExceedNumOfPopulation() +
                calculationData.getDaysFromInfectionToDeath() && calculationData.getNumberOfDaysWhenAmountOfInfectedPeopleGrowsToExceedNumOfPopulation() != 0) ||
                calculationData.getCounterFromStartOfTheSimulationToOccursMaxValueOfInfectedPeopleForSimulation() + calculationData.getDaysFromInfectionToDeath() < iterator) {
            currentSimulationDay.setNumberOfDeathPeople(0);
        } else {
            deathsCounterService.countDeathPeople(currentSimulationDay, calculationData, iterator);
            if (calculationData.getMaxNumberOfDeathPeopleForGivenData() < currentSimulationDay.getNumberOfDeathPeople()) {
                currentSimulationDay.setNumberOfDeathPeople(0);
            }
        }
    }
}
