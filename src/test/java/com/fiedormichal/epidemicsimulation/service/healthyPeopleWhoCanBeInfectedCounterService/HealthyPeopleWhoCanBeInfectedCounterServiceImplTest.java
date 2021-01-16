package com.fiedormichal.epidemicsimulation.service.healthyPeopleWhoCanBeInfectedCounterService;

import com.fiedormichal.epidemicsimulation.model.CalculationData;
import com.fiedormichal.epidemicsimulation.model.SingleDaySimulation;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import static org.junit.jupiter.api.Assertions.*;
@RunWith(SpringRunner.class)
@SpringBootTest
class HealthyPeopleWhoCanBeInfectedCounterServiceImplTest {

    @Autowired
    private HealthyPeopleWhoCanBeInfectedCounterService healthyPeopleWhoCanBeInfectedCounterService;

    @Test
    void should_set_four_thousand_of_healthy_people_who_can_be_infected() {
        //given
        SingleDaySimulation currentSimulationDay = new SingleDaySimulation();
        currentSimulationDay.setNumberOfInfectedPeople(4450);
        currentSimulationDay.setNumberOfDeathPeople(50);
        currentSimulationDay.setNumberOfPeopleWhoRecoveredAndGainedImmunity(500);
        CalculationData calculationData = CalculationData.builder()
                .population(9000)
                .build();
        //when
        healthyPeopleWhoCanBeInfectedCounterService.countHealthyPeopleWhoCanBeInfected(currentSimulationDay, calculationData);
        //then
        assertNotNull(currentSimulationDay);
        assertEquals(9000- 4450 - 50-500, currentSimulationDay.getNumberOfHealthyPeopleWhoCanBeInfected());
    }

    @Test
    void should_set_zero_of_healthy_people_who_can_be_infected_when_number_of_healthy_people_after_calculations_is_lower_than_zero() {
        //given
        SingleDaySimulation currentSimulationDay = new SingleDaySimulation();
        currentSimulationDay.setNumberOfInfectedPeople(4950);
        currentSimulationDay.setNumberOfDeathPeople(50);
        currentSimulationDay.setNumberOfPeopleWhoRecoveredAndGainedImmunity(500);
        CalculationData calculationData = CalculationData.builder()
                .population(5500)
                .build();
        //when
        healthyPeopleWhoCanBeInfectedCounterService.countHealthyPeopleWhoCanBeInfected(currentSimulationDay, calculationData);
        //then
        assertNotNull(currentSimulationDay);
        assertEquals(0, currentSimulationDay.getNumberOfHealthyPeopleWhoCanBeInfected());
    }

}