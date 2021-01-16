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

    public void changeMethodForCountingAsNeededAndSetValue(SingleDaySimulation currentSimulationDay, CalculationData calculationData, long i) {
        if (calculationData.isShouldChangeMethodForCountingNumberOfInfectedPeopleWhenMaxValueOccurs()) {
            infectedPeopleCounterService.countInfectedPeopleWhenParamReachedMaxValueForSimulation(currentSimulationDay, calculationData);
            if (i > calculationData.getDaysFromInfectionToRecovery() + calculationData.getCounterFromStartOfTheSimulationToOccursMaxValueOfInfectedPeopleForSimulation()) {
                currentSimulationDay.setNumberOfInfectedPeople(0);
            }
        } else if (calculationData.isShouldChangeMethodForCountingNumberOfInfectedPeopleWhenParamExceedNumberOfPopulation()) {
            infectedPeopleCounterService.countInfectedPeopleWhenParamExceedNumberOfPopulation(currentSimulationDay, calculationData);
        } else {
            try {
                infectedPeopleCounterService.countInfectedPeopleWhenParamIsLowerThanNumberOfPopulation(currentSimulationDay, calculationData, i);
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
