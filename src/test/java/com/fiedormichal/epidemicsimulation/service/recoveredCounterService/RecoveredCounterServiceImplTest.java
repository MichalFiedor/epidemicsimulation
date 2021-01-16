package com.fiedormichal.epidemicsimulation.service.recoveredCounterService;

import com.fiedormichal.epidemicsimulation.model.CalculationData;
import com.fiedormichal.epidemicsimulation.model.SingleDaySimulation;
import com.fiedormichal.epidemicsimulation.repository.SingleDaySimulationRepository;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.junit4.SpringRunner;

import javax.swing.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

@RunWith(SpringRunner.class)
@SpringBootTest
class RecoveredCounterServiceImplTest {

    @Autowired
    private RecoveredCounterService recoveredCounterService;

    @MockBean
    private SingleDaySimulationRepository singleDaySimulationRepository;

    @Test
    public void should_set_correct_value_of_recovered_people(){
        //given
        SingleDaySimulation simulationDayFromCurrentSimulationDayMinusPeriodBetweenInfectionAndRecovery = new SingleDaySimulation();
        simulationDayFromCurrentSimulationDayMinusPeriodBetweenInfectionAndRecovery.setId(4L);
        simulationDayFromCurrentSimulationDayMinusPeriodBetweenInfectionAndRecovery.setNumberOfInfectedPeople(5000);
        SingleDaySimulation currentSimulationDay = new SingleDaySimulation();
        List<SingleDaySimulation> singleDaySimulations = new ArrayList<>();
        for(int i=0; i<10; i++){
            singleDaySimulations.add(new SingleDaySimulation());
        }
        singleDaySimulations.add(3, simulationDayFromCurrentSimulationDayMinusPeriodBetweenInfectionAndRecovery);
        CalculationData calculationData = CalculationData.builder()
                .daysFromInfectionToRecovery(14)
                .mortalityRate(0.03)
                .singleDaySimulationsListForInitialData(singleDaySimulations)
                .build();
        int iterator=17;
        //when
        recoveredCounterService.countCurrentRecovered(currentSimulationDay, calculationData, iterator);
        //then
        assertNotNull(currentSimulationDay);
        assertEquals(5000 - (5000 * 0.03), currentSimulationDay.getNumberOfPeopleWhoRecoveredAndGainedImmunity());
    }
}