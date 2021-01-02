package com.fiedormichal.epidemicsimulation.service;

import com.fiedormichal.epidemicsimulation.model.SingleDaySimulation;
import com.fiedormichal.epidemicsimulation.repository.SingleDaySimulationRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class InfectedPeopleCounterServiceImpl implements InfectedPeopleCounterService {
    private final SingleDaySimulationRepository singleDaySimulationRepository;

    @Override
    public void countInfectedPeopleBeforeParamReachedNumberOfPopulation(SingleDaySimulation currentSimulationDay,
                                                                        double howManyPeopleWillBeInfectedByOnePerson,
                                                                        long previousDay, double mortalityRate, long daysFromInfectionToRecovery) {
        SingleDaySimulation previousSimulationDay = singleDaySimulationRepository.findById(previousDay - 1).orElseThrow();
        long deathPeopleFromCurrentDay = currentSimulationDay.getNumberOfDeathPeople();
        long recoveredPeopleFromCurrentDay = currentSimulationDay.getNumberOfPeopleWhoRecoveredAndGainedImmunity();
        long newNumberOfInfectedPeople;
        if (previousDay < 3) {
            newNumberOfInfectedPeople = Math.round(previousSimulationDay.getNumberOfInfectedPeople() * howManyPeopleWillBeInfectedByOnePerson);
            currentSimulationDay.setNumberOfInfectedPeople(newNumberOfInfectedPeople + previousSimulationDay.getNumberOfInfectedPeople() - deathPeopleFromCurrentDay - recoveredPeopleFromCurrentDay);
        } else {
            SingleDaySimulation twoDaysPreviousCurrentDaySimulation = singleDaySimulationRepository.findById(previousDay - 2).orElseThrow();
            long numberOfNewInfectedPeopleBetweenTwoSimulationDays = Math.abs(previousSimulationDay.getNumberOfInfectedPeople()
                    - twoDaysPreviousCurrentDaySimulation.getNumberOfInfectedPeople());
            newNumberOfInfectedPeople = Math.round(numberOfNewInfectedPeopleBetweenTwoSimulationDays * howManyPeopleWillBeInfectedByOnePerson) +
                    previousSimulationDay.getNumberOfInfectedPeople();

            if (deathPeopleFromCurrentDay == 0 && recoveredPeopleFromCurrentDay != 0) {
                SingleDaySimulation simulationDayFromCurrentSimulationDayMinusPeriodBetweenInfectionAndRecovery =
                        singleDaySimulationRepository.findById(previousDay - daysFromInfectionToRecovery + 1).orElseThrow();
                long futureDeaths = Math.round(simulationDayFromCurrentSimulationDayMinusPeriodBetweenInfectionAndRecovery.getNumberOfInfectedPeople() * mortalityRate);
                long totalNumberOfInfectedPeopleForCurrentDay = newNumberOfInfectedPeople + previousSimulationDay.getNumberOfInfectedPeople() +
                        futureDeaths;
                currentSimulationDay.setNumberOfInfectedPeople(totalNumberOfInfectedPeopleForCurrentDay);
            } else {
                long totalNumberOfInfectedPeopleForCurrentDay = newNumberOfInfectedPeople -
                        deathPeopleFromCurrentDay - recoveredPeopleFromCurrentDay;
                currentSimulationDay.setNumberOfInfectedPeople(totalNumberOfInfectedPeopleForCurrentDay);
            }
        }

    }

    @Override
    public void countInfectedPeopleWhenParamExceedNumberOfPopulation(SingleDaySimulation currentSimulationDay, long population) {
        long numberOFDeathPeopleForCurrentDay = currentSimulationDay.getNumberOfDeathPeople();
        long numberOfRecoveredPeople = currentSimulationDay.getNumberOfPeopleWhoRecoveredAndGainedImmunity();
        currentSimulationDay.setNumberOfInfectedPeople(population -
                numberOfRecoveredPeople - numberOFDeathPeopleForCurrentDay);
    }

    @Override
    public void countInfectedPeopleWhenParamReachedMaxValueForSimulation(SingleDaySimulation currentSimulationDay, long population, long maxValueOfInfectedPeople) {
        long numberOFDeathPeopleForCurrentDay = currentSimulationDay.getNumberOfDeathPeople();
        long numberOfRecoveredPeople = currentSimulationDay.getNumberOfPeopleWhoRecoveredAndGainedImmunity();
        currentSimulationDay.setNumberOfInfectedPeople(maxValueOfInfectedPeople - numberOFDeathPeopleForCurrentDay - numberOfRecoveredPeople);
    }
}
