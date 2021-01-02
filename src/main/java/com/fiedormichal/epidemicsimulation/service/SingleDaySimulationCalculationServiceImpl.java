package com.fiedormichal.epidemicsimulation.service;

import com.fiedormichal.epidemicsimulation.model.InitialSimulationData;
import com.fiedormichal.epidemicsimulation.model.SingleDaySimulation;
import com.fiedormichal.epidemicsimulation.repository.SingleDaySimulationRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class SingleDaySimulationCalculationServiceImpl implements SingleDaySimulationCalculationService {
    private final SingleDaySimulationRepository singleDaySimulationRepository;
    private final DeathsCounterService deathCounterService;
    private final FirstDayOfSimulationService firstDayOfSimulationService;
    private final RecoveredCounterService currentRecoveredService;
    private final InfectedPeopleCounterService infectedPeopleCounterService;
    private final HealthyPeopleWhoCanBeInfectedCounterService healthyPeopleWhoCanBeInfectedCounterService;

    private boolean shouldChangeMethodForCountingNumberOfInfectedPeople = false;
    private boolean shouldChangeMethodForCountingNumberOfInfectedPeopleWhenMaxValueOccurs =false;
    private boolean shouldSetZeroForNumberRecoveredPeople = false;
    private boolean shouldSetZeroForNumberInfectedPeople = false;
    private boolean shouldSetZeroForNumberHealthPeopleWhoCanBeInfected = false;
    private boolean setConstantValueOfPeopleWhoCanBeInfected=false;


    @Override
    public List<SingleDaySimulation> calculateEverySimulationDay(InitialSimulationData initialSimulationData) {
        SingleDaySimulation firstDayOfSimulation = firstDayOfSimulationService.createFirstDayOfSimulation(initialSimulationData);
        List<SingleDaySimulation> singleDaySimulationsListForInitialData = new ArrayList<>();
        singleDaySimulationsListForInitialData.add(firstDayOfSimulation);
        long population = initialSimulationData.getPopulationSize();
        double peopleInfectedByOnePerson = initialSimulationData.getNumberOfPeopleWhoWillBeInfectedByOnePerson();
        double mortalityRate = initialSimulationData.getMortalityRate();
        int daysFromInfectionToRecovery = initialSimulationData.getDaysFromInfectionToRecovery();
        int daysFromInfectionToDeath = initialSimulationData.getDaysFromInfectionToDeath();
        int numberOfSimulationDays = initialSimulationData.getNumberOfSimulationDays();
        long numberOfDaysWhenOccursDeath = 0;
        long maxValueOfInfectedPeople = firstDayOfSimulation.getNumberOfInfectedPeople();
        long minValueOfPeopleWhoCanBeInfected = firstDayOfSimulation.getNumberOfHealthyPeopleWhoCanBeInfected();
        long counterFromStartOfTheSimulationToMaxValueOfInfectedPeople = 0;

        for (long i = 2; i <= numberOfSimulationDays; i++) {
            long maxRecoveredPeopleForGivenData = Math.round(population - population * mortalityRate);
            SingleDaySimulation singleDaySimulation = new SingleDaySimulation();
            long maxNumberOfDeathPeople = Math.round(population * mortalityRate);

            if (i < daysFromInfectionToDeath || (i > numberOfDaysWhenOccursDeath + daysFromInfectionToDeath && numberOfDaysWhenOccursDeath != 0) ||
                    counterFromStartOfTheSimulationToMaxValueOfInfectedPeople+daysFromInfectionToDeath<i) {
                singleDaySimulation.setNumberOfDeathPeople(0);
            } else {
                deathCounterService.countDeathPeople(singleDaySimulation, mortalityRate, daysFromInfectionToDeath, i);
                if (maxNumberOfDeathPeople < singleDaySimulation.getNumberOfDeathPeople()) {
                    singleDaySimulation.setNumberOfDeathPeople(0);
                }
            }

            if (i < daysFromInfectionToRecovery || counterFromStartOfTheSimulationToMaxValueOfInfectedPeople+daysFromInfectionToRecovery<i) {
                singleDaySimulation.setNumberOfPeopleWhoRecoveredAndGainedImmunity(0);
            } else {
                if (shouldSetZeroForNumberRecoveredPeople) {
                    singleDaySimulation.setNumberOfPeopleWhoRecoveredAndGainedImmunity(0);
                } else {
                    currentRecoveredService.countCurrentRecovered(
                            singleDaySimulation, daysFromInfectionToRecovery, i, mortalityRate);
                }

                if (singleDaySimulation.getNumberOfPeopleWhoRecoveredAndGainedImmunity() == maxRecoveredPeopleForGivenData && !shouldSetZeroForNumberRecoveredPeople) {
                    shouldSetZeroForNumberRecoveredPeople = true;
                }
            }

            if(shouldChangeMethodForCountingNumberOfInfectedPeopleWhenMaxValueOccurs){
                infectedPeopleCounterService.countInfectedPeopleWhenParamReachedMaxValueForSimulation(singleDaySimulation,population, maxValueOfInfectedPeople);
            }
            infectedPeopleCounterService.countInfectedPeopleBeforeParamReachedNumberOfPopulation(singleDaySimulation,
                    peopleInfectedByOnePerson, i, mortalityRate, daysFromInfectionToRecovery);
            if(singleDaySimulation.getNumberOfInfectedPeople()>maxValueOfInfectedPeople){
                maxValueOfInfectedPeople=singleDaySimulation.getNumberOfInfectedPeople();
                counterFromStartOfTheSimulationToMaxValueOfInfectedPeople++;
            } else {
                shouldChangeMethodForCountingNumberOfInfectedPeopleWhenMaxValueOccurs =true;
            }

            if (shouldChangeMethodForCountingNumberOfInfectedPeople) {
                infectedPeopleCounterService.countInfectedPeopleWhenParamExceedNumberOfPopulation(singleDaySimulation, population);
            }

            if (singleDaySimulation.getNumberOfInfectedPeople() > population && !shouldChangeMethodForCountingNumberOfInfectedPeople) {
                numberOfDaysWhenOccursDeath = i - 1;
                shouldChangeMethodForCountingNumberOfInfectedPeople = true;
                singleDaySimulation.setNumberOfInfectedPeople(population);
            }

            if (singleDaySimulation.getNumberOfInfectedPeople() < 0 || shouldSetZeroForNumberInfectedPeople) {
                singleDaySimulation.setNumberOfInfectedPeople(0);
            }

            if(singleDaySimulation.getNumberOfInfectedPeople()==0){
                shouldSetZeroForNumberInfectedPeople=true;
            }

            if (shouldSetZeroForNumberHealthPeopleWhoCanBeInfected) {
                singleDaySimulation.setNumberOfHealthyPeopleWhoCanBeInfected(0);
            } else {
                healthyPeopleWhoCanBeInfectedCounterService.countHealthyPeopleWhoCanBeInfected(singleDaySimulation, population);
            }

            if(minValueOfPeopleWhoCanBeInfected>singleDaySimulation.getNumberOfHealthyPeopleWhoCanBeInfected()){
                minValueOfPeopleWhoCanBeInfected=singleDaySimulation.getNumberOfHealthyPeopleWhoCanBeInfected();
            }else {
                setConstantValueOfPeopleWhoCanBeInfected=true;
            }
            if(setConstantValueOfPeopleWhoCanBeInfected){
                singleDaySimulation.setNumberOfHealthyPeopleWhoCanBeInfected(minValueOfPeopleWhoCanBeInfected);
            }

            if (shouldSetZeroForNumberRecoveredPeople) {
                shouldSetZeroForNumberInfectedPeople = true;
                shouldSetZeroForNumberHealthPeopleWhoCanBeInfected = true;
            }

            singleDaySimulationsListForInitialData.add(singleDaySimulation);
            singleDaySimulationRepository.save(singleDaySimulation);
        }
        return singleDaySimulationsListForInitialData;
    }
}
