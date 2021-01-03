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
    public void changeMethodForCountingAsNeededAndSetValue(SingleDaySimulation singleDaySimulation, CalculationData calculationData, long i) {
        if (calculationData.isShouldChangeMethodForCountingNumberOfInfectedPeopleWhenMaxValueOccurs()) {
            infectedPeopleCounterService.countInfectedPeopleWhenParamReachedMaxValueForSimulation(singleDaySimulation, calculationData);
            if (i > calculationData.getDaysFromInfectionToRecovery() + calculationData.getCounterFromStartOfTheSimulationToMaxValueOfInfectedPeople()) {
                singleDaySimulation.setNumberOfInfectedPeople(0);
            }
        } else if (calculationData.isShouldChangeMethodForCountingNumberOfInfectedPeople() == true) {
            infectedPeopleCounterService.countInfectedPeopleWhenParamExceedNumberOfPopulation(singleDaySimulation, calculationData);
        } else {
            try {
                infectedPeopleCounterService.countInfectedPeopleBeforeParamReachedNumberOfPopulation(singleDaySimulation, calculationData, i);
            } catch (Exception exception) {
                exception.printStackTrace();
            }
        }
    }

    @Override
    public void setZero(SingleDaySimulation singleDaySimulation, CalculationData calculationData) {
        if(singleDaySimulation.getNumberOfInfectedPeople() < 0 || calculationData.isShouldSetZeroForNumberInfectedPeople()==true){
            singleDaySimulation.setNumberOfInfectedPeople(0);
        }
    }
}
