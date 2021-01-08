package com.fiedormichal.epidemicsimulation.service.infectedPeopleSetterService;

import com.fiedormichal.epidemicsimulation.model.CalculationData;
import com.fiedormichal.epidemicsimulation.model.SingleDaySimulation;
import com.fiedormichal.epidemicsimulation.service.infectedPeopleCounterService.InfectedPeopleCounterService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class InfectedPeopleSetterServiceImpl implements InfectedPeopleSetterService {
    private final InfectedPeopleCounterService infectedPeopleCounterService;

    @Override
    public void changeMethodForCountingAsNeededAndSetValue(SingleDaySimulation currentSimulationDay, CalculationData calculationData, long i) {
        if (calculationData.isShouldChangeMethodForCountingNumberOfInfectedPeopleWhenMaxValueOccurs()) {
            infectedPeopleCounterService.countInfectedPeopleWhenParamReachedMaxValueForSimulation(currentSimulationDay, calculationData);
            if (i > calculationData.getDaysFromInfectionToRecovery() + calculationData.getCounterFromStartOfTheSimulationToMaxValueOfInfectedPeople()) {
                currentSimulationDay.setNumberOfInfectedPeople(0);
            }
        } else if (calculationData.isShouldChangeMethodForCountingNumberOfInfectedPeople()) {
            infectedPeopleCounterService.countInfectedPeopleWhenParamExceedNumberOfPopulation(currentSimulationDay, calculationData);
        } else {
            try {
                infectedPeopleCounterService.countInfectedPeopleBeforeParamReachedNumberOfPopulation(currentSimulationDay, calculationData, i);
            } catch (Exception exception) {
                exception.printStackTrace();
            }
        }
    }

    @Override
    public void setZero(SingleDaySimulation currentSimulationDay, CalculationData calculationData) {
        if(currentSimulationDay.getNumberOfInfectedPeople() < 0 || calculationData.isShouldSetZeroForNumberInfectedPeople()){
            currentSimulationDay.setNumberOfInfectedPeople(0);
        }
    }
}
