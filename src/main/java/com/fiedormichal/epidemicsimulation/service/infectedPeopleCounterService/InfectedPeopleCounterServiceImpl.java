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
                                                                          long iterator) throws Exception {
        long currentDayId = singleDaySimulationRepository.findFirstByOrderByIdDesc().getId() + 1;
        SingleDaySimulation previousSimulationDay = singleDaySimulationRepository.findById(currentDayId - 1).orElseThrow(()->new Exception("Empty Simulation Day"));
        long deathPeopleFromCurrentDay = currentSimulationDay.getNumberOfDeathPeople();
        long recoveredPeopleFromCurrentDay = currentSimulationDay.getNumberOfPeopleWhoRecoveredAndGainedImmunity();
        long numberOfInfectedPeopleForCurrentDay;
        if (iterator < 3) {
            numberOfInfectedPeopleForCurrentDay = Math.round(previousSimulationDay.getNumberOfInfectedPeople() * calculationData.getPeopleInfectedByOnePerson());
            currentSimulationDay.setNumberOfInfectedPeople(numberOfInfectedPeopleForCurrentDay + previousSimulationDay.getNumberOfInfectedPeople() - deathPeopleFromCurrentDay - recoveredPeopleFromCurrentDay);
        } else {
            SingleDaySimulation twoDaysPreviousCurrentDaySimulation = singleDaySimulationRepository.findById(currentDayId - 2).orElseThrow(
                    ()->new Exception("Empty Simulation Day"));
            long numberOfNewInfectedPeopleBetweenTwoSimulationDays = Math.abs(previousSimulationDay.getNumberOfInfectedPeople()
                    - twoDaysPreviousCurrentDaySimulation.getNumberOfInfectedPeople());
            numberOfInfectedPeopleForCurrentDay = Math.round(numberOfNewInfectedPeopleBetweenTwoSimulationDays * calculationData.getPeopleInfectedByOnePerson()) +
                    previousSimulationDay.getNumberOfInfectedPeople();

            if (deathPeopleFromCurrentDay == 0 && recoveredPeopleFromCurrentDay != 0) {
                SingleDaySimulation simulationDayFromCurrentSimulationDayMinusPeriodBetweenInfectionAndRecovery =
                        singleDaySimulationRepository.findById(currentDayId - calculationData.getDaysFromInfectionToRecovery() + 1).orElseThrow(
                                ()->new Exception("Empty Simulation Day"));
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
