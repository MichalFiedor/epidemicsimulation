package com.fiedormichal.epidemicsimulation.service;

import com.fiedormichal.epidemicsimulation.model.CalculationData;
import com.fiedormichal.epidemicsimulation.model.InitialSimulationData;
import org.springframework.stereotype.Service;

@Service
public class CalculationDataService {
    public CalculationData createCalculationDataObject(InitialSimulationData initialSimulationData) {
        return CalculationData.builder()
                .population(initialSimulationData.getPopulationSize())
                .peopleInfectedByOnePerson(initialSimulationData.getNumberOfPeopleWhoWillBeInfectedByOnePerson())
                .mortalityRate(initialSimulationData.getMortalityRate())
                .daysFromInfectionToRecovery(initialSimulationData.getDaysFromInfectionToRecovery())
                .daysFromInfectionToDeath(initialSimulationData.getDaysFromInfectionToDeath())
                .numberOfSimulationDays(initialSimulationData.getNumberOfSimulationDays())
                .numberOfDaysWhenAmountOfInfectedPeopleGrowsToExceedNumOfPopulation(0)
                .maxNumberOfDeathPeopleForGivenData(Math.round(initialSimulationData.getPopulationSize() * initialSimulationData.getMortalityRate()))
                .maxNumberOfRecoveredPeopleForGivenData(Math.round(initialSimulationData.getPopulationSize() -
                        initialSimulationData.getPopulationSize() * initialSimulationData.getMortalityRate()))
                .counterFromStartOfTheSimulationToOccursMaxValueOfInfectedPeopleForSimulation(0)
                .shouldChangeMethodForCountingNumberOfInfectedPeopleWhenParamExceedNumberOfPopulation(false)
                .shouldChangeMethodForCountingNumberOfInfectedPeopleWhenMaxValueOccurs(false)
                .shouldSetZeroForNumberOfRecoveredPeople(false)
                .shouldSetZeroForNumberOfInfectedPeople(false)
                .shouldSetZeroForNumberOfHealthyPeopleWhoCanBeInfected(false)
                .setConstantValueOfPeopleWhoCanBeInfected(false)
                .build();
    }
}
