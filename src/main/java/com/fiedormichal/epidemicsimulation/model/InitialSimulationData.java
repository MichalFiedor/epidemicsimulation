package com.fiedormichal.epidemicsimulation.model;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.util.List;

@Entity
@Getter
@Setter
@EqualsAndHashCode
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
    @OneToMany(fetch = FetchType.LAZY, cascade = CascadeType.REMOVE)
    @JoinColumn(name = "initialSimulationData_id", insertable = false)
    private List<SingleDaySimulation> singleDaySimulations;
}
