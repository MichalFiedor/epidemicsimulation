package com.fiedormichal.epidemicsimulation.service.deathsSetterService;

import com.fiedormichal.epidemicsimulation.model.CalculationData;
import com.fiedormichal.epidemicsimulation.model.SingleDaySimulation;
import com.fiedormichal.epidemicsimulation.repository.SingleDaySimulationRepository;
import com.fiedormichal.epidemicsimulation.service.deathsCounterService.DeathsCounterService;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@RunWith(SpringRunner.class)
@SpringBootTest
class DeathsSetterServiceImplTest {

    @Autowired
    private DeathsSetterService deathsSetterService;

    @MockBean
    private SingleDaySimulationRepository singleDaySimulationRepository;

    @Test
    void should_set_deaths_number_as_zero_when_days_iterator_is_lower_than_days_from_infection_to_death() {
        //given
        SingleDaySimulation currentSimulation = new SingleDaySimulation();
        CalculationData calculationData = CalculationData.builder()
                .daysFromInfectionToDeath(14)
                .build();
        long i = 10;
        //when
        deathsSetterService.setTotalNumberOfDeathsForSingleSimulationDay(currentSimulation, calculationData, i);
        //then
        assertNotNull(currentSimulation);
        assertEquals(0, currentSimulation.getNumberOfDeathPeople());
    }

    @Test
    public void should_set_deaths_num_as_zero_when_days_iterator_is_higher_than_sum_of_days_from_infection_to_death_and_num_of_days_when_amount_of_infected_people_grows() {
        //given
        SingleDaySimulation currentSimulation = new SingleDaySimulation();
        CalculationData calculationData = CalculationData.builder()
                .daysFromInfectionToDeath(14)
                .numberOfDaysWhenAmountOfInfectedPeopleGrows(4)
                .build();
        long i = 19;
        //when
        deathsSetterService.setTotalNumberOfDeathsForSingleSimulationDay(currentSimulation, calculationData, i);
        //then
        assertNotNull(currentSimulation);
        assertEquals(0, currentSimulation.getNumberOfDeathPeople());
    }

    @Test
    public void should_set_deaths_num_as_zero_when_days_iterator_is_lower_counter_from_start_of_the_simul_to_max_val_of_infected_people_and_num_of_days_when_amount_of_infected_people_grows() {
        //given
        SingleDaySimulation currentSimulation = new SingleDaySimulation();
        CalculationData calculationData = CalculationData.builder()
                .daysFromInfectionToDeath(14)
                .counterFromStartOfTheSimulationToMaxValueOfInfectedPeople(5)
                .build();
        long i = 11;
        //when
        deathsSetterService.setTotalNumberOfDeathsForSingleSimulationDay(currentSimulation, calculationData, i);
        //then
        assertNotNull(currentSimulation);
        assertEquals(0, currentSimulation.getNumberOfDeathPeople());
    }

    @Test
    public void should_set_two_thousand_of_death_people(){
        //given
        SingleDaySimulation currentSimulation = new SingleDaySimulation();
        currentSimulation.setId(15);
        SingleDaySimulation singleDaySimulationById = mock(SingleDaySimulation.class);
        when(singleDaySimulationRepository.findFirstByOrderByIdDesc()).thenReturn(singleDaySimulationById);
        when(singleDaySimulationById.getId()).thenReturn(14L);
        SingleDaySimulation singleDaySimulation = new SingleDaySimulation();
        singleDaySimulation.setId(2L);
        singleDaySimulation.setNumberOfInfectedPeople(50000);
        Optional<SingleDaySimulation> singleDaySimulationOpt = Optional.of(singleDaySimulation);
        CalculationData calculationData = CalculationData.builder()
                .daysFromInfectionToDeath(14)
                .numberOfDaysWhenAmountOfInfectedPeopleGrows(6)
                .counterFromStartOfTheSimulationToMaxValueOfInfectedPeople(7)
                .mortalityRate(0.04)
                .maxNumberOfDeathPeople(7000)
                .build();
        long i = 19;
        when(singleDaySimulationRepository.findById(2L)).thenReturn(singleDaySimulationOpt);
        //when
        deathsSetterService.setTotalNumberOfDeathsForSingleSimulationDay(currentSimulation, calculationData, i);
        //then
        assertNotNull(currentSimulation);
        assertEquals(50000*0.04, currentSimulation.getNumberOfDeathPeople());
    }
}