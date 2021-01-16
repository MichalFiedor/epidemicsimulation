package com.fiedormichal.epidemicsimulation.service.healthyPeopleWhoCanBeInfectedSetterService;

import com.fiedormichal.epidemicsimulation.model.CalculationData;
import com.fiedormichal.epidemicsimulation.model.SingleDaySimulation;
import com.fiedormichal.epidemicsimulation.service.HealthyPeopleWhoCanBeInfectedSetterService;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import static org.junit.jupiter.api.Assertions.*;

@RunWith(SpringRunner.class)
@SpringBootTest
class HealthyPeopleWhoCanBeInfectedSetterServiceImplTest {

    @Autowired
    private HealthyPeopleWhoCanBeInfectedSetterService healthyPeopleWhoCanBeInfectedSetterService;

    @Test
    void should_set_zero_as_total_num_of_healthy_people_who_can_be_infected() {
        //given
        SingleDaySimulation currentSimulationDay = new SingleDaySimulation();
        currentSimulationDay.setNumberOfInfectedPeople(4450);
        currentSimulationDay.setNumberOfDeathPeople(50);
        currentSimulationDay.setNumberOfPeopleWhoRecoveredAndGainedImmunity(500);
        CalculationData calculationData = CalculationData.builder()
                .shouldSetZeroForNumberOfHealthyPeopleWhoCanBeInfected(true)
                .build();
        //when
        healthyPeopleWhoCanBeInfectedSetterService.setTotalNumberOfHealthyPeopleWhoCanBeInfectedForSingleSimulationDay(currentSimulationDay, calculationData);
        //then
        assertNotNull(currentSimulationDay);
        assertEquals(0, currentSimulationDay.getNumberOfHealthyPeopleWhoCanBeInfected());
    }

    @Test
    void should_set_min_value_of_people_who_can_be_infected_as_total_num_of_healthy_people_who_can_be_infected() {
        //given
        SingleDaySimulation currentSimulationDay = new SingleDaySimulation();
        currentSimulationDay.setNumberOfInfectedPeople(4450);
        currentSimulationDay.setNumberOfDeathPeople(50);
        currentSimulationDay.setNumberOfPeopleWhoRecoveredAndGainedImmunity(500);
        CalculationData calculationData = CalculationData.builder()
                .setConstantValueOfPeopleWhoCanBeInfected(true)
                .minValueOfPeopleWhoCanBeInfectedForSimulation(2464)
                .build();
        //when
        healthyPeopleWhoCanBeInfectedSetterService.setTotalNumberOfHealthyPeopleWhoCanBeInfectedForSingleSimulationDay(currentSimulationDay, calculationData);
        //then
        assertNotNull(currentSimulationDay);
        assertEquals(2464, currentSimulationDay.getNumberOfHealthyPeopleWhoCanBeInfected());
    }

    @Test
    void should_set_eight_and_half_thousand_as_total_num_of_healthy_people_who_can_be_infected() {
        //given
        SingleDaySimulation currentSimulationDay = new SingleDaySimulation();
        currentSimulationDay.setNumberOfInfectedPeople(3450);
        currentSimulationDay.setNumberOfDeathPeople(30);
        currentSimulationDay.setNumberOfPeopleWhoRecoveredAndGainedImmunity(520);
        CalculationData calculationData = CalculationData.builder()
                .population(12500)
                .build();
        //when
        healthyPeopleWhoCanBeInfectedSetterService.setTotalNumberOfHealthyPeopleWhoCanBeInfectedForSingleSimulationDay(
                currentSimulationDay, calculationData);
        //then
        assertNotNull(currentSimulationDay);
        assertEquals(12500 - 3450 - 30 - 520, currentSimulationDay.getNumberOfHealthyPeopleWhoCanBeInfected());
    }
}