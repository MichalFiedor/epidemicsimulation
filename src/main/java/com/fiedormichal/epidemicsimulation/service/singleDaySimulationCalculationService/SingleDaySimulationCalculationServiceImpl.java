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
        calculationData.setMaxValueOfInfectedPeople(firstDayOfSimulation.getNumberOfInfectedPeople());
        calculationData.setMinValueOfPeopleWhoCanBeInfected(firstDayOfSimulation.getNumberOfHealthyPeopleWhoCanBeInfected());

        for (long i = 2; i <= calculationData.getNumberOfSimulationDays(); i++) {
            SingleDaySimulation singleDaySimulation = new SingleDaySimulation();

            deathsSetterService.setTotalNumberOfDeathsForSingleSimulationDay(singleDaySimulation, calculationData, i);

            recoveredSetterService.setTotalNumberOfRecoveredForSingleSimulationDay(singleDaySimulation, calculationData, i);

            if (singleDaySimulation.getNumberOfPeopleWhoRecoveredAndGainedImmunity() == calculationData.getMaxRecoveredPeopleForGivenData() &&
                    !calculationData.isShouldSetZeroForNumberRecoveredPeople()&&calculationData.getMortalityRate()!=1.0) {
                calculationData.setShouldSetZeroForNumberRecoveredPeople(true);
            }

            infectedPeopleSetterService.changeMethodForCountingAsNeededAndSetValue(singleDaySimulation, calculationData, i);

            if (singleDaySimulation.getNumberOfInfectedPeople() > calculationData.getPopulation() &&
                    calculationData.isShouldChangeMethodForCountingNumberOfInfectedPeople() == false) {
                calculationData.setNumberOfDaysWhenAmountOfInfectedPeopleGrows(i - 1);
                calculationData.setShouldChangeMethodForCountingNumberOfInfectedPeople(true);
                singleDaySimulation.setNumberOfInfectedPeople(calculationData.getPopulation());
            }

            changeCalculationMethodOfInfectedPeopleIfRequired(singleDaySimulation, calculationData);

            infectedPeopleSetterService.setZero(singleDaySimulation, calculationData);

            if (singleDaySimulation.getNumberOfInfectedPeople() == 0) {
                calculationData.setShouldSetZeroForNumberInfectedPeople(true);
            }

            healthyPeopleWhoCanBeInfectedSetterService.setTotalNumberOfHealthyPeopleWhoCanBeInfectedForSingleSimulationDay(
                    singleDaySimulation, calculationData);

            if (calculationData.getMinValueOfPeopleWhoCanBeInfected() > singleDaySimulation.getNumberOfHealthyPeopleWhoCanBeInfected()) {
                calculationData.setMinValueOfPeopleWhoCanBeInfected(singleDaySimulation.getNumberOfHealthyPeopleWhoCanBeInfected());
            } else {
                calculationData.setSetConstantValueOfPeopleWhoCanBeInfected(true);
                singleDaySimulation.setNumberOfHealthyPeopleWhoCanBeInfected(calculationData.getMinValueOfPeopleWhoCanBeInfected());
            }

            if (calculationData.isShouldSetZeroForNumberRecoveredPeople()) {
                calculationData.setShouldSetZeroForNumberInfectedPeople(true);
                calculationData.setShouldSetZeroForNumberHealthyPeopleWhoCanBeInfected(true);
            }

            singleDaySimulationsListForInitialData.add(singleDaySimulation);
            singleDaySimulationRepository.save(singleDaySimulation);

        }
        return singleDaySimulationsListForInitialData;
    }

        private void changeCalculationMethodOfInfectedPeopleIfRequired(SingleDaySimulation singleDaySimulation, CalculationData calculationData){
        if (singleDaySimulation.getNumberOfInfectedPeople() > calculationData.getMaxValueOfInfectedPeople()) {
            calculationData.setMaxValueOfInfectedPeople(singleDaySimulation.getNumberOfInfectedPeople());
            long counter = calculationData.getCounterFromStartOfTheSimulationToMaxValueOfInfectedPeople();
            calculationData.setCounterFromStartOfTheSimulationToMaxValueOfInfectedPeople(counter+1);
        } else {
            calculationData.setShouldChangeMethodForCountingNumberOfInfectedPeopleWhenMaxValueOccurs(true);
        }
    }
}
