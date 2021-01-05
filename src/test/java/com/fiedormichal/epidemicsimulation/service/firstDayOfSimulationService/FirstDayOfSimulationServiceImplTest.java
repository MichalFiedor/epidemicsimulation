package com.fiedormichal.epidemicsimulation.service.firstDayOfSimulationService;

import com.fiedormichal.epidemicsimulation.model.InitialSimulationData;
import com.fiedormichal.epidemicsimulation.model.SingleDaySimulation;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import static org.junit.jupiter.api.Assertions.*;

@RunWith(SpringRunner.class)
@SpringBootTest
class FirstDayOfSimulationServiceImplTest {

    @Autowired
    private FirstDayOfSimulationService firstDayOfSimulationService;


    @Test
    void shouldReturnFirstDaySimulationObject() {
        //given
        InitialSimulationData initialSimulationData = new InitialSimulationData();
        initialSimulationData.setInitialNumberOfInfected(100);
        initialSimulationData.setPopulationSize(5000);
        long healthyPeopleWhoCanBeInfected = initialSimulationData.getPopulationSize() - initialSimulationData.getInitialNumberOfInfected();
        //when
        SingleDaySimulation result = firstDayOfSimulationService.createFirstDayOfSimulation(initialSimulationData);
        //then
        assertNotNull(result);
        assertEquals(initialSimulationData.getInitialNumberOfInfected(), result.getNumberOfInfectedPeople());
        assertEquals(healthyPeopleWhoCanBeInfected, result.getNumberOfHealthyPeopleWhoCanBeInfected());
        assertEquals(0, result.getNumberOfDeathPeople());
        assertEquals(0, result.getNumberOfPeopleWhoRecoveredAndGainedImmunity());

    }
}