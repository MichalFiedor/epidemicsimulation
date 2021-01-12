package com.fiedormichal.epidemicsimulation.service.singleDaySimulationCalculationService;

import com.fiedormichal.epidemicsimulation.model.CalculationData;
import com.fiedormichal.epidemicsimulation.model.InitialSimulationData;
import com.fiedormichal.epidemicsimulation.model.SingleDaySimulation;
import com.fiedormichal.epidemicsimulation.repository.SingleDaySimulationRepository;
import com.fiedormichal.epidemicsimulation.service.calculationDataService.CalculationDataService;
import com.fiedormichal.epidemicsimulation.service.deathsSetterService.DeathsSetterService;
import com.fiedormichal.epidemicsimulation.service.firstDayOfSimulationService.FirstDayOfSimulationService;
import com.fiedormichal.epidemicsimulation.service.healthyPeopleWhoCanBeInfectedSetterService.HealthyPeopleWhoCanBeInfectedSetterService;
import com.fiedormichal.epidemicsimulation.service.infectedPeopleSetterService.InfectedPeopleSetterService;
import com.fiedormichal.epidemicsimulation.service.recoveredSetterService.RecoveredSetterService;
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
    private final CalculationDataService calculationDataService;
    private final HealthyPeopleWhoCanBeInfectedSetterService healthyPeopleWhoCanBeInfectedSetterService;

    @Override
    public List<SingleDaySimulation> calculateEverySimulationDay(InitialSimulationData initialSimulationData) {
        SingleDaySimulation firstDayOfSimulation = firstDayOfSimulationService.createFirstDayOfSimulation(initialSimulationData);
        List<SingleDaySimulation> singleDaySimulationsListForInitialData = new ArrayList<>();
        singleDaySimulationsListForInitialData.add(firstDayOfSimulation);
        CalculationData calculationData = calculationDataService.createCalculationDataObject(initialSimulationData);
        calculationData.setMaxValueOfInfectedPeopleForAllSimulation(firstDayOfSimulation.getNumberOfInfectedPeople());
        calculationData.setMinValueOfPeopleWhoCanBeInfectedForSimulation(firstDayOfSimulation.getNumberOfHealthyPeopleWhoCanBeInfected());

        for (long i = 2; i <= calculationData.getNumberOfSimulationDays(); i++) {
            SingleDaySimulation currentSimulationDay = new SingleDaySimulation();

            deathsSetterService.setTotalNumberOfDeathsForSingleSimulationDay(currentSimulationDay, calculationData, i);

            recoveredSetterService.setTotalNumberOfRecoveredForSingleSimulationDay(currentSimulationDay, calculationData, i);

            if (currentSimulationDay.getNumberOfPeopleWhoRecoveredAndGainedImmunity() == calculationData.getMaxNumberOfRecoveredPeopleForGivenData() &&
                    !calculationData.isShouldSetZeroForNumberOfRecoveredPeople() && calculationData.getMortalityRate() != 1.0) {
                calculationData.setShouldSetZeroForNumberOfRecoveredPeople(true);
            }

            infectedPeopleSetterService.changeMethodForCountingAsNeededAndSetValue(currentSimulationDay, calculationData, i);

            if (currentSimulationDay.getNumberOfInfectedPeople() > calculationData.getPopulation() &&
                    calculationData.isShouldChangeMethodForCountingNumberOfInfectedPeopleWhenParamExceedNumberOfPopulation() == false) {
                calculationData.setNumberOfDaysWhenAmountOfInfectedPeopleGrowsToExceedNumOfPopulation(i - 1);
                calculationData.setShouldChangeMethodForCountingNumberOfInfectedPeopleWhenParamExceedNumberOfPopulation(true);
                currentSimulationDay.setNumberOfInfectedPeople(calculationData.getPopulation());
            }

            changeCalculationMethodOfInfectedPeopleIfRequired(currentSimulationDay, calculationData);

            infectedPeopleSetterService.setZero(currentSimulationDay, calculationData);

            if (currentSimulationDay.getNumberOfInfectedPeople() == 0) {
                calculationData.setShouldSetZeroForNumberOfInfectedPeople(true);
            }

            healthyPeopleWhoCanBeInfectedSetterService.setTotalNumberOfHealthyPeopleWhoCanBeInfectedForSingleSimulationDay(
                    currentSimulationDay, calculationData);

            if (calculationData.getMinValueOfPeopleWhoCanBeInfectedForSimulation() > currentSimulationDay.getNumberOfHealthyPeopleWhoCanBeInfected()) {
                calculationData.setMinValueOfPeopleWhoCanBeInfectedForSimulation(currentSimulationDay.getNumberOfHealthyPeopleWhoCanBeInfected());
            } else {
                calculationData.setSetConstantValueOfPeopleWhoCanBeInfected(true);
                currentSimulationDay.setNumberOfHealthyPeopleWhoCanBeInfected(calculationData.getMinValueOfPeopleWhoCanBeInfectedForSimulation());
            }

            if (calculationData.isShouldSetZeroForNumberOfRecoveredPeople()) {
                calculationData.setShouldSetZeroForNumberOfInfectedPeople(true);
                calculationData.setShouldSetZeroForNumberOfHealthyPeopleWhoCanBeInfected(true);
            }

            singleDaySimulationsListForInitialData.add(currentSimulationDay);
            singleDaySimulationRepository.save(currentSimulationDay);

        }
        return singleDaySimulationsListForInitialData;
    }

    private void changeCalculationMethodOfInfectedPeopleIfRequired(SingleDaySimulation currentSimulationDay, CalculationData calculationData) {
        if (currentSimulationDay.getNumberOfInfectedPeople() > calculationData.getMaxValueOfInfectedPeopleForAllSimulation()) {
            calculationData.setMaxValueOfInfectedPeopleForAllSimulation(currentSimulationDay.getNumberOfInfectedPeople());
            long counter = calculationData.getCounterFromStartOfTheSimulationToOccursMaxValueOfInfectedPeopleForSimulation();
            calculationData.setCounterFromStartOfTheSimulationToOccursMaxValueOfInfectedPeopleForSimulation(counter + 1);
        } else {
            calculationData.setShouldChangeMethodForCountingNumberOfInfectedPeopleWhenMaxValueOccurs(true);
        }
    }
}
