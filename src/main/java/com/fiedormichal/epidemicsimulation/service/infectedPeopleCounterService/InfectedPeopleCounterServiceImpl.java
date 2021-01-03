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
    public void countInfectedPeopleBeforeParamReachedNumberOfPopulation(SingleDaySimulation currentSimulationDay,
                                                                        CalculationData calculationData,
                                                                        long iterator) {
        long lastRecordId = singleDaySimulationRepository.findFirstByOrderByIdDesc().getId() + 1;
        SingleDaySimulation previousSimulationDay = singleDaySimulationRepository.findById(lastRecordId - 1).orElseThrow();
        ;
        long deathPeopleFromCurrentDay = currentSimulationDay.getNumberOfDeathPeople();
        long recoveredPeopleFromCurrentDay = currentSimulationDay.getNumberOfPeopleWhoRecoveredAndGainedImmunity();
        long newNumberOfInfectedPeople;
        if (iterator < 3) {
            newNumberOfInfectedPeople = Math.round(previousSimulationDay.getNumberOfInfectedPeople() * calculationData.getPeopleInfectedByOnePerson());
            currentSimulationDay.setNumberOfInfectedPeople(newNumberOfInfectedPeople + previousSimulationDay.getNumberOfInfectedPeople() - deathPeopleFromCurrentDay - recoveredPeopleFromCurrentDay);
        } else {
            SingleDaySimulation twoDaysPreviousCurrentDaySimulation = singleDaySimulationRepository.findById(lastRecordId - 2).orElseThrow();
            long numberOfNewInfectedPeopleBetweenTwoSimulationDays = Math.abs(previousSimulationDay.getNumberOfInfectedPeople()
                    - twoDaysPreviousCurrentDaySimulation.getNumberOfInfectedPeople());
            newNumberOfInfectedPeople = Math.round(numberOfNewInfectedPeopleBetweenTwoSimulationDays * calculationData.getPeopleInfectedByOnePerson()) +
                    previousSimulationDay.getNumberOfInfectedPeople();

            if (deathPeopleFromCurrentDay == 0 && recoveredPeopleFromCurrentDay != 0) {
                SingleDaySimulation simulationDayFromCurrentSimulationDayMinusPeriodBetweenInfectionAndRecovery =
                        singleDaySimulationRepository.findById(lastRecordId - calculationData.getDaysFromInfectionToRecovery() + 1).orElseThrow();
                long futureDeaths = Math.round(
                        simulationDayFromCurrentSimulationDayMinusPeriodBetweenInfectionAndRecovery.getNumberOfInfectedPeople() * calculationData.getMortalityRate());
                long totalNumberOfInfectedPeopleForCurrentDay = newNumberOfInfectedPeople +
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
    public void countInfectedPeopleWhenParamExceedNumberOfPopulation(SingleDaySimulation currentSimulationDay, CalculationData calculationData) {
        long numberOFDeathPeopleForCurrentDay = currentSimulationDay.getNumberOfDeathPeople();
        long numberOfRecoveredPeople = currentSimulationDay.getNumberOfPeopleWhoRecoveredAndGainedImmunity();
        currentSimulationDay.setNumberOfInfectedPeople(calculationData.getPopulation() -
                numberOfRecoveredPeople - numberOFDeathPeopleForCurrentDay);
    }

    @Override
    public void countInfectedPeopleWhenParamReachedMaxValueForSimulation(SingleDaySimulation currentSimulationDay, CalculationData calculationData) {
        long numberOFDeathPeopleForCurrentDay = currentSimulationDay.getNumberOfDeathPeople();
        long numberOfRecoveredPeople = currentSimulationDay.getNumberOfPeopleWhoRecoveredAndGainedImmunity();
        currentSimulationDay.setNumberOfInfectedPeople(calculationData.getMaxValueOfInfectedPeople() - numberOFDeathPeopleForCurrentDay - numberOfRecoveredPeople);
    }
}
