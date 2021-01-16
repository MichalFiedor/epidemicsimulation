package com.fiedormichal.epidemicsimulation.model;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;

@Builder
@Getter
@Setter
public class CalculationData {
    private long population;
    private double peopleInfectedByOnePerson;
    private double mortalityRate;
    private int daysFromInfectionToRecovery;
    private int daysFromInfectionToDeath;
    private int numberOfSimulationDays;
    private long numberOfDaysWhenAmountOfInfectedPeopleGrowsToExceedNumOfPopulation;
    private long maxValueOfInfectedPeopleForAllSimulation;
    private long minValueOfPeopleWhoCanBeInfectedForSimulation;
    private long counterFromStartOfTheSimulationToOccursMaxValueOfInfectedPeopleForSimulation;
    private long maxNumberOfDeathPeopleForGivenData;
    private long maxNumberOfRecoveredPeopleForGivenData;
    private List<SingleDaySimulation>singleDaySimulationsListForInitialData;
    private boolean shouldChangeMethodForCountingNumberOfInfectedPeopleWhenParamExceedNumberOfPopulation;
    private boolean shouldChangeMethodForCountingNumberOfInfectedPeopleWhenMaxValueOccurs;
    private boolean shouldSetZeroForNumberOfRecoveredPeople;
    private boolean shouldSetZeroForNumberOfInfectedPeople;
    private boolean shouldSetZeroForNumberOfHealthyPeopleWhoCanBeInfected;
    private boolean setConstantValueOfPeopleWhoCanBeInfected;
}
