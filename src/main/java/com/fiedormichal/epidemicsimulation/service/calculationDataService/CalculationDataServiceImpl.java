package com.fiedormichal.epidemicsimulation.service.calculationDataService;

import com.fiedormichal.epidemicsimulation.model.CalculationData;
import com.fiedormichal.epidemicsimulation.model.InitialSimulationData;
import org.springframework.stereotype.Service;

@Service
public class CalculationDataServiceImpl implements CalculationDataService{
    @Override
    public CalculationData createCalculationDataObject(InitialSimulationData initialSimulationData) {
        return CalculationData.builder()
                .population(initialSimulationData.getPopulationSize())
                .peopleInfectedByOnePerson(initialSimulationData.getNumberOfPeopleWhoWillBeInfectedByOnePerson())
                .mortalityRate(initialSimulationData.getMortalityRate())
                .daysFromInfectionToRecovery(initialSimulationData.getDaysFromInfectionToRecovery())
                .daysFromInfectionToDeath(initialSimulationData.getDaysFromInfectionToDeath())
                .numberOfSimulationDays(initialSimulationData.getNumberOfSimulationDays())
                .numberOfDaysWhenAmountOfInfectedPeopleGrows(0)
                .maxNumberOfDeathPeople(Math.round(initialSimulationData.getPopulationSize() * initialSimulationData.getMortalityRate()))
                .maxRecoveredPeopleForGivenData(Math.round(initialSimulationData.getPopulationSize() -
                        initialSimulationData.getPopulationSize() * initialSimulationData.getMortalityRate()))
                .counterFromStartOfTheSimulationToMaxValueOfInfectedPeople(0)
                .shouldChangeMethodForCountingNumberOfInfectedPeople(false)
                .shouldChangeMethodForCountingNumberOfInfectedPeopleWhenMaxValueOccurs(false)
                .shouldSetZeroForNumberRecoveredPeople(false)
                .shouldSetZeroForNumberInfectedPeople(false)
                .shouldSetZeroForNumberHealthyPeopleWhoCanBeInfected(false)
                .setConstantValueOfPeopleWhoCanBeInfected(false)
                .build();
    }
}
