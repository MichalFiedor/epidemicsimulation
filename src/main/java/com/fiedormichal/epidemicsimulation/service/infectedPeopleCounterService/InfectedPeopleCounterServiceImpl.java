package com.fiedormichal.epidemicsimulation.service.infectedPeopleCounterService;

import com.fiedormichal.epidemicsimulation.model.CalculationData;
import com.fiedormichal.epidemicsimulation.model.SingleDaySimulation;
import com.fiedormichal.epidemicsimulation.repository.SingleDaySimulationRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class InfectedPeopleCounterServiceImpl implements InfectedPeopleCounterService {
    private final SingleDaySimulationRepository singleDaySimulationRepository;

    @Override
    public void countInfectedPeopleWhenParamIsLowerThanNumberOfPopulation(SingleDaySimulation currentSimulationDay,
                                                                          CalculationData calculationData,
                                                                          int iterator) {
        SingleDaySimulation previousSimulationDay = calculationData.getSingleDaySimulationsListForInitialData().get(iterator - 1);
        long deathPeopleFromCurrentDay = currentSimulationDay.getNumberOfDeathPeople();
        long recoveredPeopleFromCurrentDay = currentSimulationDay.getNumberOfPeopleWhoRecoveredAndGainedImmunity();
        long numberOfInfectedPeopleForCurrentDay;
        if (iterator < 2) {
            numberOfInfectedPeopleForCurrentDay = Math.round(previousSimulationDay.getNumberOfInfectedPeople() * calculationData.getPeopleInfectedByOnePerson());
            currentSimulationDay.setNumberOfInfectedPeople(numberOfInfectedPeopleForCurrentDay + previousSimulationDay.getNumberOfInfectedPeople() - deathPeopleFromCurrentDay - recoveredPeopleFromCurrentDay);
        } else {
            SingleDaySimulation twoDaysPreviousCurrentDaySimulation = calculationData.getSingleDaySimulationsListForInitialData().get(iterator - 2);
            long numberOfNewInfectedPeopleBetweenTwoSimulationDays = Math.abs(previousSimulationDay.getNumberOfInfectedPeople()
                    - twoDaysPreviousCurrentDaySimulation.getNumberOfInfectedPeople());
            numberOfInfectedPeopleForCurrentDay = Math.round(numberOfNewInfectedPeopleBetweenTwoSimulationDays * calculationData.getPeopleInfectedByOnePerson()) +
                    previousSimulationDay.getNumberOfInfectedPeople();

            if (deathPeopleFromCurrentDay == 0 && recoveredPeopleFromCurrentDay != 0) {
                SingleDaySimulation simulationDayFromCurrentSimulationDayMinusPeriodBetweenInfectionAndRecovery =
                        calculationData.getSingleDaySimulationsListForInitialData().get(iterator - calculationData.getDaysFromInfectionToRecovery());
                long futureDeaths = Math.round(
                        simulationDayFromCurrentSimulationDayMinusPeriodBetweenInfectionAndRecovery.getNumberOfInfectedPeople() * calculationData.getMortalityRate());
                long numberOfInfectedPeopleWithFutureDeathsForCurrentDay = numberOfInfectedPeopleForCurrentDay +
                        futureDeaths;
                currentSimulationDay.setNumberOfInfectedPeople(numberOfInfectedPeopleWithFutureDeathsForCurrentDay);
            } else {
                long totalNumberOfInfectedPeopleForCurrentDay = numberOfInfectedPeopleForCurrentDay -
                        deathPeopleFromCurrentDay - recoveredPeopleFromCurrentDay;
                currentSimulationDay.setNumberOfInfectedPeople(totalNumberOfInfectedPeopleForCurrentDay);
            }
        }
    }

    @Override
    public void countInfectedPeopleWhenParamExceedNumberOfPopulation(SingleDaySimulation currentSimulationDay, CalculationData calculationData) {
        long numberOFDeathPeople = currentSimulationDay.getNumberOfDeathPeople();
        long numberOfRecoveredPeople = currentSimulationDay.getNumberOfPeopleWhoRecoveredAndGainedImmunity();
        currentSimulationDay.setNumberOfInfectedPeople(calculationData.getPopulation() -
                numberOfRecoveredPeople - numberOFDeathPeople);
    }

    @Override
    public void countInfectedPeopleWhenParamReachedMaxValueForSimulation(SingleDaySimulation currentSimulationDay, CalculationData calculationData) {
        long numberOFDeathPeopleForCurrentDay = currentSimulationDay.getNumberOfDeathPeople();
        long numberOfRecoveredPeople = currentSimulationDay.getNumberOfPeopleWhoRecoveredAndGainedImmunity();
        currentSimulationDay.setNumberOfInfectedPeople(calculationData.getMaxValueOfInfectedPeopleForAllSimulation() - numberOFDeathPeopleForCurrentDay - numberOfRecoveredPeople);
    }
}
