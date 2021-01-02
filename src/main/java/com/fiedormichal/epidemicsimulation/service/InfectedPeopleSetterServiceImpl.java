package com.fiedormichal.epidemicsimulation.service;

import com.fiedormichal.epidemicsimulation.model.SingleDaySimulation;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class InfectedPeopleSetterServiceImpl implements InfectedPeopleSetterService {
    private final InfectedPeopleCounterService infectedPeopleCounterService;

    @Override
    public void changeMethodForCountingAsNeededAndSetValue(SingleDaySimulation singleDaySimulation, long population,
                                                                                                      long maxValueOfInfectedPeople, double peopleInfectedByOnePerson,
                                                                                                      double mortalityRate, int daysFromInfectionToRecovery, long i,
                                                                                                      long counterFromStartOfTheSimulationToMaxValueOfInfectedPeople,
                                                                                                      boolean shouldChangeMethodForCountingNumberOfInfectedPeopleWhenMaxValueOccurs,
                                                                                                      int shouldChangeMethodForCountingNumberOfInfectedPeople) {
        if (shouldChangeMethodForCountingNumberOfInfectedPeopleWhenMaxValueOccurs) {
            infectedPeopleCounterService.countInfectedPeopleWhenParamReachedMaxValueForSimulation(singleDaySimulation, population, maxValueOfInfectedPeople);
            if (i > daysFromInfectionToRecovery + counterFromStartOfTheSimulationToMaxValueOfInfectedPeople) {
                singleDaySimulation.setNumberOfInfectedPeople(0);
            }
        } else if (shouldChangeMethodForCountingNumberOfInfectedPeople == 1) {
            infectedPeopleCounterService.countInfectedPeopleWhenParamExceedNumberOfPopulation(singleDaySimulation, population);
        } else {
            infectedPeopleCounterService.countInfectedPeopleBeforeParamReachedNumberOfPopulation(singleDaySimulation,
                    peopleInfectedByOnePerson, i, mortalityRate, daysFromInfectionToRecovery);
        }
    }

    @Override
    public void setZero(SingleDaySimulation singleDaySimulation, int shouldSetZeroForNumberInfectedPeople) {
        if(singleDaySimulation.getNumberOfInfectedPeople() < 0 || shouldSetZeroForNumberInfectedPeople==1){
            singleDaySimulation.setNumberOfInfectedPeople(0);
        }
    }
}
