package com.fiedormichal.epidemicsimulation.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;

@Entity
@Getter
@Setter
public class SingleDaySimulation {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;
    private long numberOfInfectedPeople;
    private long numberOfHealthyPeopleWhoCanBeInfected;
    private long numberOfDeathPeople;
    private long numberOfPeopleWhoRecoveredAndGainedImmunity;
}
