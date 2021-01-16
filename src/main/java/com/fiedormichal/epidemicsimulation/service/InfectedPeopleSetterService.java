package com.fiedormichal.epidemicsimulation.service;

import com.fiedormichal.epidemicsimulation.model.CalculationData;
import com.fiedormichal.epidemicsimulation.model.SingleDaySimulation;
import com.fiedormichal.epidemicsimulation.service.infectedPeopleCounterService.InfectedPeopleCounterService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class InfectedPeopleSetterService {
    private final InfectedPeopleCounterService infectedPeopleCounterService;

    public void changeMethodForCountingAsNeededAndSetValue(SingleDaySimulation currentSimulationDay, CalculationData calculationData, int iterator) {
        if (calculationData.isShouldChangeMethodForCountingNumberOfInfectedPeopleWhenMaxValueOccurs()) {
            infectedPeopleCounterService.countInfectedPeopleWhenParamReachedMaxValueForSimulation(currentSimulationDay, calculationData);
            if (iterator > calculationData.getDaysFromInfectionToRecovery() + calculationData.getCounterFromStartOfTheSimulationToOccursMaxValueOfInfectedPeopleForSimulation()) {
                currentSimulationDay.setNumberOfInfectedPeople(0);
            }
        } else if (calculationData.isShouldChangeMethodForCountingNumberOfInfectedPeopleWhenParamExceedNumberOfPopulation()) {
            infectedPeopleCounterService.countInfectedPeopleWhenParamExceedNumberOfPopulation(currentSimulationDay, calculationData);
        } else {
            try {
                infectedPeopleCounterService.countInfectedPeopleWhenParamIsLowerThanNumberOfPopulation(currentSimulationDay, calculationData, iterator);
            } catch (Exception exception) {
                exception.printStackTrace();
            }
        }
    }

    public void setZero(SingleDaySimulation currentSimulationDay, CalculationData calculationData) {
        if(currentSimulationDay.getNumberOfInfectedPeople() < 0 || calculationData.isShouldSetZeroForNumberOfInfectedPeople()){
            currentSimulationDay.setNumberOfInfectedPeople(0);
        }
    }
}
