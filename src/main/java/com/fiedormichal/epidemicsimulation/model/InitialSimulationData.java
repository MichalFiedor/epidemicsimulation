package com.fiedormichal.epidemicsimulation.model;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import javax.validation.constraints.*;
import java.util.List;

@Entity
@Getter
@Setter
@EqualsAndHashCode
public class InitialSimulationData {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;
    @NotBlank(message = "You must enter the simulation name.")
    private String simulationName;
    @Min(value = 1000, message = "The population must be equal or greater than 1000.")
    private long populationSize;
    @Min(value = 1, message = "Initial number of infected people must be at least 1.")
    private long initialNumberOfInfected;
    @NotNull(message = "You must enter double value of number of people who will be infected by one person.")
    private Double numberOfPeopleWhoWillBeInfectedByOnePerson;
    @NotNull(message = "You must enter double value of mortality rate.")
    private Double mortalityRate;
    @Min(value = 1, message = "Number of days from infection to recovery must be greater than 1.")
    private int daysFromInfectionToRecovery;
    @Min(value = 1, message = "Number of days from infection to death must be greater than 1.")
    private int daysFromInfectionToDeath;
    @Min(value = 1, message = "Number of simulation days must be at least 1.")
    @Max(value = 100, message = "You can run a simulation for up to 100 days")
    private int numberOfSimulationDays;
    @OneToMany(fetch = FetchType.LAZY, cascade = CascadeType.REMOVE)
    @JoinColumn(name = "initialSimulationData_id", insertable = false)
    private List<SingleDaySimulation> singleDaySimulations;
}
