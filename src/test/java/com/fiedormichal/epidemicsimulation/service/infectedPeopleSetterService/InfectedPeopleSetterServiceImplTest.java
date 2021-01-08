package com.fiedormichal.epidemicsimulation.service.infectedPeopleSetterService;

import com.fiedormichal.epidemicsimulation.model.CalculationData;
import com.fiedormichal.epidemicsimulation.model.SingleDaySimulation;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.mock;

@RunWith(SpringRunner.class)
@SpringBootTest
class InfectedPeopleSetterServiceImplTest {

    @Autowired
    private InfectedPeopleSetterService infectedPeopleSetterService;

    @Test
    public void should_set_zero_as_number_of_infected_people_when_number_of_infected_people_for_current_simulation_day_is_lower_than_zero() {
        //given
        SingleDaySimulation currentSimulationDay = new SingleDaySimulation();
        currentSimulationDay.setNumberOfInfectedPeople(-100);
        CalculationData calculationData = mock(CalculationData.class);
        //when
        infectedPeopleSetterService.setZero(currentSimulationDay, calculationData);
        //then
        assertNotNull(currentSimulationDay);
        assertEquals(0, currentSimulationDay.getNumberOfInfectedPeople());
    }

    @Test
    public void should_set_zero_as_number_of_infected_people_when_should_set_zero_for_number_of_infected_people_is_true() {
        //given
        SingleDaySimulation currentSimulationDay = new SingleDaySimulation();
        CalculationData calculationData = CalculationData.builder()
                .shouldSetZeroForNumberInfectedPeople(true)
                .build();
        //when
        infectedPeopleSetterService.setZero(currentSimulationDay, calculationData);
        //then
        assertNotNull(currentSimulationDay);
        assertEquals(0, currentSimulationDay.getNumberOfInfectedPeople());
    }

    @Test
    void setZero() {
    }
}