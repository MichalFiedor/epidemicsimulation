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
    public void setTotalNumberOfDeathsForSingleSimulationDay(SingleDaySimulation singleDaySimulation, CalculationData calculationData, long i) {

        if (i < calculationData.getDaysFromInfectionToDeath() || (i > calculationData.getNumberOfDaysWhenAmountOfInfectedPeopleGrows() +
                calculationData.getDaysFromInfectionToDeath() && calculationData.getNumberOfDaysWhenAmountOfInfectedPeopleGrows() != 0) ||
                calculationData.getCounterFromStartOfTheSimulationToMaxValueOfInfectedPeople() + calculationData.getDaysFromInfectionToDeath() < i) {
            singleDaySimulation.setNumberOfDeathPeople(0);
        } else {
            deathsCounterService.countDeathPeople(singleDaySimulation, calculationData);
            if (calculationData.getMaxNumberOfDeathPeople() < singleDaySimulation.getNumberOfDeathPeople()) {
                singleDaySimulation.setNumberOfDeathPeople(0);
            }
        }
    }
}
