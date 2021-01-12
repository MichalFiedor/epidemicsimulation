package com.fiedormichal.epidemicsimulation.service.deathsSetterService;

import com.fiedormichal.epidemicsimulation.model.CalculationData;
import com.fiedormichal.epidemicsimulation.model.SingleDaySimulation;
import com.fiedormichal.epidemicsimulation.service.deathsCounterService.DeathsCounterService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class DeathsSetterServiceImpl implements DeathsSetterService {
    private final DeathsCounterService deathsCounterService;

    @Override
    public void setTotalNumberOfDeathsForSingleSimulationDay(SingleDaySimulation currentSimulationDay, CalculationData calculationData, long i) {

        if (i < calculationData.getDaysFromInfectionToDeath() || (i > calculationData.getNumberOfDaysWhenAmountOfInfectedPeopleGrowsToExceedNumOfPopulation() +
                calculationData.getDaysFromInfectionToDeath() && calculationData.getNumberOfDaysWhenAmountOfInfectedPeopleGrowsToExceedNumOfPopulation() != 0) ||
                calculationData.getCounterFromStartOfTheSimulationToOccursMaxValueOfInfectedPeopleForSimulation() + calculationData.getDaysFromInfectionToDeath() < i) {
            currentSimulationDay.setNumberOfDeathPeople(0);
        } else {
            deathsCounterService.countDeathPeople(currentSimulationDay, calculationData);
            if (calculationData.getMaxNumberOfDeathPeopleForGivenData() < currentSimulationDay.getNumberOfDeathPeople()) {
                currentSimulationDay.setNumberOfDeathPeople(0);
            }
        }
    }
}
