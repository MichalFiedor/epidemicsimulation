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
    boolean changeMethodForCountingNumberOfInfectedPeople =false;

    @Override
    public List<SingleDaySimulation> calculateEverySimulationDay(InitialSimulationData initialSimulationData) {
        SingleDaySimulation firstDayOfSimulation = createFirstDayOfSimulationBasedOnTheInitialData(initialSimulationData);
        List<SingleDaySimulation> singleDaySimulationsListForInitialData = new ArrayList<>();
        singleDaySimulationsListForInitialData.add(firstDayOfSimulation);
        long population = firstDayOfSimulation.getPopulation();
        double peopleInfectedByOnePerson = initialSimulationData.getHowManyPeopleWillBeInfectedByOnePerson();
        double mortalityRate = initialSimulationData.getMortalityRate();
        int daysFromInfectionToRecovery = initialSimulationData.getDaysFromInfectionToRecovery();
        int daysFromInfectionToDeath = initialSimulationData.getDaysFromInfectionToDeath();
        int numberOfSimulationDays = initialSimulationData.getNumberOfSimulationDays();
        boolean shouldSetZeroForNumberOfHealthyPeopleWhoCanBeInfected =false;

        for (long i = 2; i <= numberOfSimulationDays; i++) {
            SingleDaySimulation singleDaySimulation = new SingleDaySimulation();
            singleDaySimulation.setPopulation(population);
            if (i < daysFromInfectionToDeath) {
                singleDaySimulation.setNumberOfDeathPeople(0);
            } else {
                countDeathPeopleWhenDaysIteratorIsGreaterThanDaysFromInfectionToDeath(
                        singleDaySimulation, mortalityRate, daysFromInfectionToDeath, i);
            }
            if (i < daysFromInfectionToRecovery) {
                singleDaySimulation.setNumberOfPeopleWhoRecoveredAndGainedImmunity(0);
            } else {
                countCurrentRecovered(
                        singleDaySimulation, daysFromInfectionToRecovery, i);
            }
            countInfectedPeopleBeforeParamReachedNumberOfPopulation(singleDaySimulation, peopleInfectedByOnePerson, i);
            if(!changeMethodForCountingNumberOfInfectedPeople){
                if(singleDaySimulation.getNumberOfInfectedPeople()==singleDaySimulation.getPopulation()){
                    changeMethodForCountingNumberOfInfectedPeople=true;
                }
            } else {
                countInfectedPeopleWhenParamExceedNumberOfPopulation(singleDaySimulation, i);
            }
            if(singleDaySimulation.getNumberOfInfectedPeople()<0){
                singleDaySimulation.setNumberOfInfectedPeople(0);
            }
            countHealthyPeopleWhoCanBeInfected(singleDaySimulation);
            if(!shouldSetZeroForNumberOfHealthyPeopleWhoCanBeInfected){
                if(singleDaySimulation.getNumberOfHealthyPeopleWhoCanBeInfected()==0){
                    shouldSetZeroForNumberOfHealthyPeopleWhoCanBeInfected=true;
                }
            }else {
                singleDaySimulation.setNumberOfHealthyPeopleWhoCanBeInfected(0);
            }
            singleDaySimulationsListForInitialData.add(singleDaySimulation);
            singleDaySimulationRepository.save(singleDaySimulation);
        }
        return singleDaySimulationsListForInitialData;
    }

    private SingleDaySimulation createFirstDayOfSimulationBasedOnTheInitialData(InitialSimulationData initialSimulationData) {
        SingleDaySimulation firstDayOfSimulation = new SingleDaySimulation();

        firstDayOfSimulation.setNumberOfInfectedPeople(
                initialSimulationData.getInitialNumberOfInfected());

        firstDayOfSimulation.setNumberOfHealthyPeopleWhoCanBeInfected(
                initialSimulationData.getPopulationSize() - initialSimulationData.getInitialNumberOfInfected());

        firstDayOfSimulation.setNumberOfDeathPeople(0);

        firstDayOfSimulation.setNumberOfPeopleWhoRecoveredAndGainedImmunity(0);
        firstDayOfSimulation.setPopulation(initialSimulationData.getPopulationSize());
        singleDaySimulationRepository.save(firstDayOfSimulation);

        return firstDayOfSimulation;
    }

    private SingleDaySimulation countDeathPeopleWhenDaysIteratorIsGreaterThanDaysFromInfectionToDeath(
            SingleDaySimulation currentSimulationDay,
            double mortalityRate, int daysFromInfectionToDeath, long previousDay) {

        SingleDaySimulation simulationDayFromCurrentSimulationDayMinusPeriodBetweenInfectionAndDeath =
                singleDaySimulationRepository.findById(previousDay - daysFromInfectionToDeath + 1).orElseThrow();
        SingleDaySimulation previousSimulationDay = singleDaySimulationRepository.findById(previousDay - 1).orElseThrow();
        long infectedPeople = simulationDayFromCurrentSimulationDayMinusPeriodBetweenInfectionAndDeath.getNumberOfInfectedPeople();
//        long infectedPeopleFromPreviousSimulationDay = previousSimulationDay.getNumberOfInfectedPeople();

        long numberOfDeathPeopleForCurrentSimulationDay = (long) Math.floor(infectedPeople * mortalityRate);
        currentSimulationDay.setNumberOfDeathPeople(numberOfDeathPeopleForCurrentSimulationDay);
//        currentSimulationDay.setNumberOfInfectedPeople(
//                infectedPeopleFromPreviousSimulationDay - numberOfDeathPeopleForCurrentSimulationDay);
        return currentSimulationDay;
    }

    private SingleDaySimulation countCurrentRecovered(
            SingleDaySimulation currentSimulationDay,
            long daysFromInfectionToRecovery, long previousDay) {

        SingleDaySimulation simulationDayFromCurrentSimulationDayMinusPeriodBetweenInfectionAndRecovery =
                singleDaySimulationRepository.findById(previousDay - daysFromInfectionToRecovery + 1).orElseThrow();
        SingleDaySimulation previousSimulationDay = singleDaySimulationRepository.findById(previousDay - 1).orElseThrow();
        long currentNumberOfDeathPeople = currentSimulationDay.getNumberOfDeathPeople();
        long numberOfInfectedPeople = simulationDayFromCurrentSimulationDayMinusPeriodBetweenInfectionAndRecovery.getNumberOfInfectedPeople();
        long totalRecoveredPeople = numberOfInfectedPeople + previousSimulationDay.getNumberOfPeopleWhoRecoveredAndGainedImmunity() - currentNumberOfDeathPeople;
        currentSimulationDay.setNumberOfPeopleWhoRecoveredAndGainedImmunity(totalRecoveredPeople);
        return currentSimulationDay;
    }

    private SingleDaySimulation countInfectedPeopleBeforeParamReachedNumberOfPopulation(SingleDaySimulation currentSimulationDay,
                                                                                  double howManyPeopleWillBeInfectedByOnePerson, long previousDay) {
        SingleDaySimulation previousSimulationDay = singleDaySimulationRepository.findById(previousDay - 1).orElseThrow();
        long deathPeopleFromCurrentDay = currentSimulationDay.getNumberOfDeathPeople();
        long recoveredPeopleFromCurrentDay = currentSimulationDay.getNumberOfPeopleWhoRecoveredAndGainedImmunity();
        if (previousDay < 3) {
            long newNumberOfInfectedPeople = Math.round(previousSimulationDay.getNumberOfInfectedPeople() * howManyPeopleWillBeInfectedByOnePerson);
            currentSimulationDay.setNumberOfInfectedPeople(newNumberOfInfectedPeople-deathPeopleFromCurrentDay-recoveredPeopleFromCurrentDay);
        } else {
            SingleDaySimulation twoDaysPreviousCurrentDaySimulation = singleDaySimulationRepository.findById(previousDay - 2).orElseThrow();
            long numberOfNewInfectedPeopleBetweenTwoSimulationDays = previousSimulationDay.getNumberOfInfectedPeople()
                    - twoDaysPreviousCurrentDaySimulation.getNumberOfInfectedPeople();
            long newNumberOfInfectedPeople = Math.round(numberOfNewInfectedPeopleBetweenTwoSimulationDays * howManyPeopleWillBeInfectedByOnePerson);
            long totalNumberOfInfectedPeopleForCurrentDay= previousSimulationDay.getNumberOfInfectedPeople() + newNumberOfInfectedPeople;
            if(totalNumberOfInfectedPeopleForCurrentDay>currentSimulationDay.getPopulation()){
                currentSimulationDay.setNumberOfInfectedPeople(currentSimulationDay.getPopulation()-deathPeopleFromCurrentDay-recoveredPeopleFromCurrentDay);
            }else {
                currentSimulationDay.setNumberOfInfectedPeople(totalNumberOfInfectedPeopleForCurrentDay
                        -deathPeopleFromCurrentDay-recoveredPeopleFromCurrentDay);
            }

        }
        return currentSimulationDay;
    }

    private SingleDaySimulation countInfectedPeopleWhenParamExceedNumberOfPopulation(SingleDaySimulation currentSimulationDay, long previousDay) {
        SingleDaySimulation previousSimulationDay = singleDaySimulationRepository.findById(previousDay - 1).orElseThrow();
        long numberOFDeathPeopleForCurrentDay = currentSimulationDay.getNumberOfDeathPeople();
        long numberOfRecoveredPeople = currentSimulationDay.getNumberOfPeopleWhoRecoveredAndGainedImmunity();
        currentSimulationDay.setNumberOfInfectedPeople(currentSimulationDay.getPopulation()-
                numberOfRecoveredPeople- numberOFDeathPeopleForCurrentDay);
        return currentSimulationDay;
    }

    private SingleDaySimulation countHealthyPeopleWhoCanBeInfected(SingleDaySimulation currentSimulationDay) {
        long healthyPeopleWhoCanBeInfected = currentSimulationDay.getPopulation() - currentSimulationDay.getNumberOfInfectedPeople()
                - currentSimulationDay.getNumberOfDeathPeople() - currentSimulationDay.getNumberOfPeopleWhoRecoveredAndGainedImmunity();
            currentSimulationDay.setNumberOfHealthyPeopleWhoCanBeInfected(healthyPeopleWhoCanBeInfected);
        return currentSimulationDay;
    }
}
