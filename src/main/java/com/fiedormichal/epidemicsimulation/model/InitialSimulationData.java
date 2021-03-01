package com.fiedormichal.epidemicsimulation.model;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotBlank;
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
    private double numberOfPeopleWhoWillBeInfectedByOnePerson;
    private double mortalityRate;
    private int daysFromInfectionToRecovery;
    private int daysFromInfectionToDeath;
    private int numberOfSimulationDays;
    @OneToMany(fetch = FetchType.LAZY, cascade = CascadeType.REMOVE)
    @JoinColumn(name = "initialSimulationData_id", insertable = false)
    private List<SingleDaySimulation> singleDaySimulations;
}
