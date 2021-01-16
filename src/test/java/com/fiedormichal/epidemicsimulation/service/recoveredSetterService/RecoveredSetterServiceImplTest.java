package com.fiedormichal.epidemicsimulation.service.recoveredSetterService;

import com.fiedormichal.epidemicsimulation.model.CalculationData;
import com.fiedormichal.epidemicsimulation.model.SingleDaySimulation;
import com.fiedormichal.epidemicsimulation.repository.SingleDaySimulationRepository;
import com.fiedormichal.epidemicsimulation.service.RecoveredSetterService;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

@RunWith(SpringRunner.class)
@SpringBootTest
class RecoveredSetterServiceImplTest {

    @Autowired
    private RecoveredSetterService recoveredSetterService;

    @MockBean
    private SingleDaySimulationRepository singleDaySimulationRepository;

    @Test
    void should_set_zero_when_days_iterator_is_lower_than_time_from_infection_to_recovery() {
        //given
        SingleDaySimulation currentSimulationDay = new SingleDaySimulation();
        CalculationData calculationData= CalculationData.builder()
                .daysFromInfectionToRecovery(14)
                .build();
        long iterator= 10;
        //when
        recoveredSetterService.setTotalNumberOfRecoveredForSingleSimulationDay(currentSimulationDay, calculationData, iterator);
        //then
        assertNotNull(currentSimulationDay);
        assertEquals(0, currentSimulationDay.getNumberOfPeopleWhoRecoveredAndGainedImmunity());
    }

    @Test
    void should_set_zero_when_sum_time_from_infection_to_recovery_and_counter_to_max_value_of_infected_people_is_lower_than_days_iterator() {
        //given
        SingleDaySimulation currentSimulationDay = new SingleDaySimulation();
        CalculationData calculationData= CalculationData.builder()
                .daysFromInfectionToRecovery(14)
                .counterFromStartOfTheSimulationToOccursMaxValueOfInfectedPeopleForSimulation(5)
                .build();
        long iterator= 20;
        //when
        recoveredSetterService.setTotalNumberOfRecoveredForSingleSimulationDay(currentSimulationDay, calculationData, iterator);
        //then
        assertNotNull(currentSimulationDay);
        assertEquals(0, currentSimulationDay.getNumberOfPeopleWhoRecoveredAndGainedImmunity());
    }

    @Test
    void method_set_zero_when_should_set_zero_for_number_recovered_people_param_is_true() {
        //given
        SingleDaySimulation currentSimulationDay = new SingleDaySimulation();
        CalculationData calculationData= CalculationData.builder()
                .daysFromInfectionToRecovery(14)
                .counterFromStartOfTheSimulationToOccursMaxValueOfInfectedPeopleForSimulation(8)
                .shouldSetZeroForNumberOfRecoveredPeople(true)
                .build();
        long iterator= 21;
        //when
        recoveredSetterService.setTotalNumberOfRecoveredForSingleSimulationDay(currentSimulationDay, calculationData, iterator);
        //then
        assertNotNull(currentSimulationDay);
        assertEquals(0, currentSimulationDay.getNumberOfPeopleWhoRecoveredAndGainedImmunity());
    }

    @Test
    void should_set_correct_value_of_recovered_people() {
        //given
        SingleDaySimulation singleDaySimulationForLastRecordId = mock(SingleDaySimulation.class);
        when(singleDaySimulationRepository.findFirstByOrderByIdDesc()).thenReturn(singleDaySimulationForLastRecordId);
        when(singleDaySimulationForLastRecordId.getId()).thenReturn(22L);
        SingleDaySimulation simulationDayFromCurrentSimulationDayMinusPeriodBetweenInfectionAndRecovery = new SingleDaySimulation();
        simulationDayFromCurrentSimulationDayMinusPeriodBetweenInfectionAndRecovery.setId(10L);
        simulationDayFromCurrentSimulationDayMinusPeriodBetweenInfectionAndRecovery.setNumberOfInfectedPeople(25000);
        when(singleDaySimulationRepository.findById(10L)).thenReturn(
                Optional.of(simulationDayFromCurrentSimulationDayMinusPeriodBetweenInfectionAndRecovery));
        SingleDaySimulation currentSimulationDay = new SingleDaySimulation();
        CalculationData calculationData = CalculationData.builder()
                .daysFromInfectionToRecovery(14)
                .counterFromStartOfTheSimulationToOccursMaxValueOfInfectedPeopleForSimulation(8)
                .mortalityRate(0.05)
                .build();
        long iterator= 21;

        //when
        recoveredSetterService.setTotalNumberOfRecoveredForSingleSimulationDay(currentSimulationDay, calculationData, iterator);
        //then
        assertNotNull(currentSimulationDay);
        assertEquals(25000 - (25000 * 0.05), currentSimulationDay.getNumberOfPeopleWhoRecoveredAndGainedImmunity());
    }

}