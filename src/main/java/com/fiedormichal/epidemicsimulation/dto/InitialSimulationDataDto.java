package com.fiedormichal.epidemicsimulation.dto;

import lombok.Builder;
import lombok.Getter;

@Builder
@Getter
public class InitialSimulationDataDto {
    private long id;
    private String simulationName;
    private long populationSize;
    private long initialNumberOfInfected;
    private double numberOfPeopleWhoWillBeInfectedByOnePerson;
    private double mortalityRate;
    private int daysFromInfectionToRecovery;
    private int daysFromInfectionToDeath;
    private int numberOfSimulationDays;
    private boolean isDeleted;
}
