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


    @Override
    public List<SingleDaySimulation> calculateEverySimulationDay(InitialSimulationData initialSimulationData) {
        SingleDaySimulation firstDayOfSimulation = createFirstDayOfSimulationBasedOnTheInitialData(initialSimulationData);
        List<SingleDaySimulation> singleDaySimulationsListForInitialData = new ArrayList<>();
        singleDaySimulationsListForInitialData.add(firstDayOfSimulation);
        long population = firstDayOfSimulation.getPopulation();
        double howManyPeopleWillBeInfectedByOnePerson = initialSimulationData.getHowManyPeopleWillBeInfectedByOnePerson();
        double mortalityRate = initialSimulationData.getMortalityRate();
        int daysFromInfectionToRecovery = initialSimulationData.getDaysFromInfectionToRecovery();
        int daysFromInfectionToDeath = initialSimulationData.getDaysFromInfectionToDeath();
        int numberOfSimulationDays = initialSimulationData.getNumberOfSimulationDays();
        boolean setZeroForNumberOfHealthyPeopleWhoCanBeInfected =false;
        boolean changeMethodForCountingNumberOfInfectedPeople =false;

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
                countRecoveredPeopleWhenDaysIteratorIsGreaterThanDaysFromInfectionToRecovery(
                        singleDaySimulation, daysFromInfectionToRecovery, i);
            }
            countInfectedPeopleBeforeParamReachedNumberOfPopulation(singleDaySimulation, howManyPeopleWillBeInfectedByOnePerson, i);

            countHealthyPeopleWhoCanBeInfected(singleDaySimulation);
            if(!setZeroForNumberOfHealthyPeopleWhoCanBeInfected){
                if(singleDaySimulation.getNumberOfHealthyPeopleWhoCanBeInfected()==0){
                    setZeroForNumberOfHealthyPeopleWhoCanBeInfected=true;
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
            double mortalityRate, int daysFromInfectionToDeath, long daysIterator) {

        SingleDaySimulation simulationDayFromCurrentSimulationDayMinusPeriodBetweenInfectionAndDeath =
                singleDaySimulationRepository.findById(daysIterator - daysFromInfectionToDeath + 1).orElseThrow();
        SingleDaySimulation previousSimulationDay = singleDaySimulationRepository.findById(daysIterator - 1).orElseThrow();
        long infectedPeople = simulationDayFromCurrentSimulationDayMinusPeriodBetweenInfectionAndDeath.getNumberOfInfectedPeople();
//        long infectedPeopleFromPreviousSimulationDay = previousSimulationDay.getNumberOfInfectedPeople();

        long numberOfDeathPeopleForCurrentSimulationDay = (long) Math.floor(infectedPeople * mortalityRate);
        currentSimulationDay.setNumberOfDeathPeople(numberOfDeathPeopleForCurrentSimulationDay);
//        currentSimulationDay.setNumberOfInfectedPeople(
//                infectedPeopleFromPreviousSimulationDay - numberOfDeathPeopleForCurrentSimulationDay);

        return currentSimulationDay;
    }

    private SingleDaySimulation countRecoveredPeopleWhenDaysIteratorIsGreaterThanDaysFromInfectionToRecovery(
            SingleDaySimulation currentSimulationDay,
            long daysFromInfectionToRecovery, long daysIterator) {

        SingleDaySimulation simulationDayFromCurrentSimulationDayMinusPeriodBetweenInfectionAndRecovery =
                singleDaySimulationRepository.findById(daysIterator - daysFromInfectionToRecovery + 1).orElseThrow();
//        SingleDaySimulation previousSimulationDay = singleDaySimulationRepository.findById(daysIterator - 1).orElseThrow();

        long numberOfRecoveredPeople = simulationDayFromCurrentSimulationDayMinusPeriodBetweenInfectionAndRecovery.getNumberOfInfectedPeople();
//        currentSimulationDay.setNumberOfInfectedPeople(previousSimulationDay.getNumberOfInfectedPeople() - numberOfRecoveredPeople);
        currentSimulationDay.setNumberOfPeopleWhoRecoveredAndGainedImmunity(numberOfRecoveredPeople);
        return currentSimulationDay;
    }

    private SingleDaySimulation countInfectedPeopleBeforeParamReachedNumberOfPopulation(SingleDaySimulation currentSimulationDay,
                                                                                  double howManyPeopleWillBeInfectedByOnePerson, long daysIterator) {
        SingleDaySimulation previousSimulationDay = singleDaySimulationRepository.findById(daysIterator - 1).orElseThrow();
        long deathPeopleFromCurrentDay = currentSimulationDay.getNumberOfDeathPeople();
        long recoveredPeopleFromCurrentDay = currentSimulationDay.getNumberOfPeopleWhoRecoveredAndGainedImmunity();
        if (daysIterator < 3) {
            long newNumberOfInfectedPeople = Math.round(previousSimulationDay.getNumberOfInfectedPeople() * howManyPeopleWillBeInfectedByOnePerson);
            currentSimulationDay.setNumberOfInfectedPeople(newNumberOfInfectedPeople-deathPeopleFromCurrentDay-recoveredPeopleFromCurrentDay);
        } else {
            SingleDaySimulation twoDaysPreviousCurrentDaySimulation = singleDaySimulationRepository.findById(daysIterator - 2).orElseThrow();
            long numberOfNewInfectedPeopleBetweenTwoSimulationDays = previousSimulationDay.getNumberOfInfectedPeople()
                    - twoDaysPreviousCurrentDaySimulation.getNumberOfInfectedPeople();
            long newNumberOfInfectedPeople = Math.round(numberOfNewInfectedPeopleBetweenTwoSimulationDays * howManyPeopleWillBeInfectedByOnePerson);
            long totalNumberOfInfectedPeopleForCurrentDay= previousSimulationDay.getNumberOfInfectedPeople() + newNumberOfInfectedPeople;
            if(totalNumberOfInfectedPeopleForCurrentDay>currentSimulationDay.getPopulation()){
                currentSimulationDay.setNumberOfInfectedPeople(currentSimulationDay.getPopulation()-deathPeopleFromCurrentDay-recoveredPeopleFromCurrentDay);
            }else {
                currentSimulationDay.setNumberOfInfectedPeople(previousSimulationDay.getNumberOfInfectedPeople() + newNumberOfInfectedPeople
                        -deathPeopleFromCurrentDay-recoveredPeopleFromCurrentDay);
            }
        }
        return currentSimulationDay;
    }

    private SingleDaySimulation countInfectedPeopleWhenParamExceedNumberOfPopulation(SingleDaySimulation currentSimulationDay, long daysIterator) {
        SingleDaySimulation previousSimulationDay = singleDaySimulationRepository.findById(daysIterator - 1).orElseThrow();
        long numberOFDeathPeopleForCurrentDay = currentSimulationDay.getNumberOfDeathPeople();
        long numberOfRecoveredPeople = currentSimulationDay.getNumberOfPeopleWhoRecoveredAndGainedImmunity();
        currentSimulationDay.setNumberOfInfectedPeople(previousSimulationDay.getNumberOfInfectedPeople()-numberOFDeathPeopleForCurrentDay-numberOfRecoveredPeople);
        return currentSimulationDay;
    }

    private SingleDaySimulation countHealthyPeopleWhoCanBeInfected(SingleDaySimulation currentSimulationDay) {
        long healthyPeopleWhoCanBeInfected = currentSimulationDay.getPopulation() - currentSimulationDay.getNumberOfInfectedPeople()
                - currentSimulationDay.getNumberOfDeathPeople() - currentSimulationDay.getNumberOfPeopleWhoRecoveredAndGainedImmunity();
            currentSimulationDay.setNumberOfHealthyPeopleWhoCanBeInfected(healthyPeopleWhoCanBeInfected);
        return currentSimulationDay;
    }

    private boolean checkIfSumOfAllParamIsGreaterThanPopulation(SingleDaySimulation singleDaySimulation) {
        long numberOfInfectedPeople = singleDaySimulation.getNumberOfInfectedPeople();
        long numberOfHealthyPeopleWhoCanBeInfected = singleDaySimulation.getNumberOfHealthyPeopleWhoCanBeInfected();
        long numberOfDeathPeople = singleDaySimulation.getNumberOfDeathPeople();
        long numberOfPeopleWhoRecoveredAndGainedImmunity = singleDaySimulation.getNumberOfPeopleWhoRecoveredAndGainedImmunity();
        long population = singleDaySimulation.getPopulation();
        long sum = numberOfInfectedPeople + numberOfHealthyPeopleWhoCanBeInfected + numberOfDeathPeople + numberOfPeopleWhoRecoveredAndGainedImmunity;
        if (sum > population) {
            return true;
        }
        return false;
    }
}
