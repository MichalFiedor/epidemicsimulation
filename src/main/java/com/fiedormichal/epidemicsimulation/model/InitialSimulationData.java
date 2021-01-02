package com.fiedormichal.epidemicsimulation.model;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.util.List;

@Entity
@Getter
@Setter
public class InitialSimulationData {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;
    private String simulationName;
    private long populationSize;
    private long initialNumberOfInfected;
    private double numberOfPeopleWhoWillBeInfectedByOnePerson;
    private double mortalityRate;
    private int daysFromInfectionToRecovery;
    private int daysFromInfectionToDeath;
    private int numberOfSimulationDays;
    private boolean isDeleted = false;
    @OneToMany(fetch = FetchType.EAGER, cascade = CascadeType.ALL)
    @JoinTable(name = "initialdata_singledaysimulation")
    private List<SingleDaySimulation> singleDaySimulations;
}
