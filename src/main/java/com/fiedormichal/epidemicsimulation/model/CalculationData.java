package com.fiedormichal.epidemicsimulation.model;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

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
    private long numberOfDaysWhenAmountOfInfectedPeopleGrows;
    private long maxValueOfInfectedPeople;
    private long minValueOfPeopleWhoCanBeInfected;
    private long counterFromStartOfTheSimulationToMaxValueOfInfectedPeople;
    private long maxNumberOfDeathPeople;
    private long maxRecoveredPeopleForGivenData;
    private boolean shouldChangeMethodForCountingNumberOfInfectedPeople;
    private boolean shouldChangeMethodForCountingNumberOfInfectedPeopleWhenMaxValueOccurs;
    private boolean shouldSetZeroForNumberRecoveredPeople ;
    private boolean shouldSetZeroForNumberInfectedPeople;
    private boolean shouldSetZeroForNumberHealthyPeopleWhoCanBeInfected;
    private boolean setConstantValueOfPeopleWhoCanBeInfected;
}
