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
    private final DeathsSetterService deathsSetterService;
    private final FirstDayOfSimulationService firstDayOfSimulationService;
    private final RecoveredSetterService recoveredSetterService;
    private final InfectedPeopleSetterService infectedPeopleSetterService;
    private final HealthyPeopleWhoCanBeInfectedSetterService healthyPeopleWhoCanBeInfectedSetterService;

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
        long numberOfDaysWhenAmountOfInfectedPeopleGrows = 0;
        long maxValueOfInfectedPeople = firstDayOfSimulation.getNumberOfInfectedPeople();
        long minValueOfPeopleWhoCanBeInfected = firstDayOfSimulation.getNumberOfHealthyPeopleWhoCanBeInfected();
        long counterFromStartOfTheSimulationToMaxValueOfInfectedPeople = 0;
        int shouldChangeMethodForCountingNumberOfInfectedPeople = 0;
        boolean shouldChangeMethodForCountingNumberOfInfectedPeopleWhenMaxValueOccurs = false;
        boolean shouldSetZeroForNumberRecoveredPeople = false;
        int shouldSetZeroForNumberInfectedPeople = 0;
        boolean shouldSetZeroForNumberHealthPeopleWhoCanBeInfected = false;
        boolean setConstantValueOfPeopleWhoCanBeInfected = false;

        for (long i = 2; i <= numberOfSimulationDays; i++) {
            long maxRecoveredPeopleForGivenData = Math.round(population - population * mortalityRate);
            SingleDaySimulation singleDaySimulation = new SingleDaySimulation();
            long maxNumberOfDeathPeople = Math.round(population * mortalityRate);

            deathsSetterService.setTotalNumberOfDeathsForSingleSimulationDay(singleDaySimulation, daysFromInfectionToDeath, numberOfDaysWhenAmountOfInfectedPeopleGrows,
                    counterFromStartOfTheSimulationToMaxValueOfInfectedPeople, mortalityRate, maxNumberOfDeathPeople, i, singleDaySimulationsListForInitialData);

            recoveredSetterService.setTotalNumberOfRecoveredForSingleSimulationDay(singleDaySimulation, daysFromInfectionToRecovery,
                    counterFromStartOfTheSimulationToMaxValueOfInfectedPeople, shouldSetZeroForNumberRecoveredPeople, mortalityRate, i);

            if (singleDaySimulation.getNumberOfPeopleWhoRecoveredAndGainedImmunity() == maxRecoveredPeopleForGivenData &&
                    !shouldSetZeroForNumberRecoveredPeople) {
                shouldSetZeroForNumberRecoveredPeople = true;
            }

            infectedPeopleSetterService.changeMethodForCountingAsNeededAndSetValue(
                    singleDaySimulation, population, maxValueOfInfectedPeople, peopleInfectedByOnePerson, mortalityRate, daysFromInfectionToRecovery,
                    i, counterFromStartOfTheSimulationToMaxValueOfInfectedPeople, shouldChangeMethodForCountingNumberOfInfectedPeopleWhenMaxValueOccurs,
                    shouldChangeMethodForCountingNumberOfInfectedPeople);

            if (singleDaySimulation.getNumberOfInfectedPeople() > maxValueOfInfectedPeople) {
                maxValueOfInfectedPeople = singleDaySimulation.getNumberOfInfectedPeople();
                counterFromStartOfTheSimulationToMaxValueOfInfectedPeople++;
            } else {
                shouldChangeMethodForCountingNumberOfInfectedPeopleWhenMaxValueOccurs = true;
            }

            if (singleDaySimulation.getNumberOfInfectedPeople() > population && shouldChangeMethodForCountingNumberOfInfectedPeople == 0) {
                numberOfDaysWhenAmountOfInfectedPeopleGrows = i - 1;
                shouldChangeMethodForCountingNumberOfInfectedPeople = 1;
                singleDaySimulation.setNumberOfInfectedPeople(population);
            }

            infectedPeopleSetterService.setZero(singleDaySimulation, shouldSetZeroForNumberInfectedPeople);

            if (singleDaySimulation.getNumberOfInfectedPeople() == 0) {
                shouldSetZeroForNumberInfectedPeople = 1;
            }

            healthyPeopleWhoCanBeInfectedSetterService.setTotalNumberOfHealthyPeopleWhoCanBeInfectedForSingleSimulationDay(
                    singleDaySimulation, shouldSetZeroForNumberHealthPeopleWhoCanBeInfected, setConstantValueOfPeopleWhoCanBeInfected,
                    minValueOfPeopleWhoCanBeInfected, population);

            if (minValueOfPeopleWhoCanBeInfected > singleDaySimulation.getNumberOfHealthyPeopleWhoCanBeInfected()) {
                minValueOfPeopleWhoCanBeInfected = singleDaySimulation.getNumberOfHealthyPeopleWhoCanBeInfected();
            } else {
                setConstantValueOfPeopleWhoCanBeInfected = true;
                singleDaySimulation.setNumberOfHealthyPeopleWhoCanBeInfected(minValueOfPeopleWhoCanBeInfected);
            }

            if (shouldSetZeroForNumberRecoveredPeople) {
                shouldSetZeroForNumberInfectedPeople = 1;
                shouldSetZeroForNumberHealthPeopleWhoCanBeInfected = true;
            }

            singleDaySimulationsListForInitialData.add(singleDaySimulation);
            singleDaySimulation.setInitialSimulationData(initialSimulationData);
//            singleDaySimulationRepository.save(singleDaySimulation);
        }
        return singleDaySimulationsListForInitialData;
    }
}
