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
    private final DeathCounterService deathCounterService;
    private final FirstDayOfSimulationService firstDayOfSimulationService;
    private final RecoveredCounterService currentRecoveredService;
    private final InfectedPeopleCounterService infectedPeopleCounterService;
    private final HealthyPeopleWhoCanBeInfectedCounterService healthyPeopleWhoCanBeInfectedCounterService;

    boolean shouldChangeMethodForCountingNumberOfInfectedPeople = false;
    boolean shouldSetZeroForRecoveredPeople = false;
    boolean shouldSetZeroForInfectedPeople = false;
    boolean shouldSetZeroForHealthPeopleWhoCanBeInfected = false;

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

        for (long i = 2; i <= numberOfSimulationDays; i++) {
            long maxRecoveredPeopleForGivenData = Math.round(population - population * mortalityRate);
            SingleDaySimulation singleDaySimulation = new SingleDaySimulation();
            long maxNumberOfDeathPeople = Math.round(population * mortalityRate);

            if (i < daysFromInfectionToDeath || (i > numberOfDaysWhenOccursDeath + daysFromInfectionToDeath && numberOfDaysWhenOccursDeath != 0)) {
                singleDaySimulation.setNumberOfDeathPeople(0);
            } else {
                deathCounterService.countDeathPeople(singleDaySimulation, mortalityRate, daysFromInfectionToDeath, i);
                if (maxNumberOfDeathPeople < singleDaySimulation.getNumberOfDeathPeople()) {
                    singleDaySimulation.setNumberOfDeathPeople(0);
                }
            }
            if (i < daysFromInfectionToRecovery) {
                singleDaySimulation.setNumberOfPeopleWhoRecoveredAndGainedImmunity(0);
            } else {
                if (shouldSetZeroForRecoveredPeople) {
                    singleDaySimulation.setNumberOfPeopleWhoRecoveredAndGainedImmunity(0);
                } else {
                    currentRecoveredService.countCurrentRecovered(
                            singleDaySimulation, daysFromInfectionToRecovery, i, mortalityRate);
                }
                if (singleDaySimulation.getNumberOfPeopleWhoRecoveredAndGainedImmunity() == maxRecoveredPeopleForGivenData && !shouldSetZeroForRecoveredPeople) {
                    shouldSetZeroForRecoveredPeople = true;
                }
            }
            infectedPeopleCounterService.countInfectedPeopleBeforeParamReachedNumberOfPopulation(singleDaySimulation,
                    peopleInfectedByOnePerson, i, mortalityRate, daysFromInfectionToRecovery);
            if (shouldChangeMethodForCountingNumberOfInfectedPeople) {
                infectedPeopleCounterService.countInfectedPeopleWhenParamExceedNumberOfPopulation(singleDaySimulation, population);
            }
            if (singleDaySimulation.getNumberOfInfectedPeople() > population && !shouldChangeMethodForCountingNumberOfInfectedPeople) {
                numberOfDaysWhenOccursDeath = i - 1;
                shouldChangeMethodForCountingNumberOfInfectedPeople = true;
                singleDaySimulation.setNumberOfInfectedPeople(population);
            }
            if (singleDaySimulation.getNumberOfInfectedPeople() < 0 || shouldSetZeroForInfectedPeople) {
                singleDaySimulation.setNumberOfInfectedPeople(0);
            }
            if (shouldSetZeroForHealthPeopleWhoCanBeInfected) {
                singleDaySimulation.setNumberOfHealthyPeopleWhoCanBeInfected(0);
            } else {
                healthyPeopleWhoCanBeInfectedCounterService.countHealthyPeopleWhoCanBeInfected(singleDaySimulation, population);
            }
            if (shouldSetZeroForRecoveredPeople) {
                shouldSetZeroForInfectedPeople = true;
                shouldSetZeroForHealthPeopleWhoCanBeInfected = true;
            }
            singleDaySimulationsListForInitialData.add(singleDaySimulation);
            singleDaySimulationRepository.save(singleDaySimulation);
        }
        return singleDaySimulationsListForInitialData;
    }
}
