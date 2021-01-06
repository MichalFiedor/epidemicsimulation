package com.fiedormichal.epidemicsimulation.service.infectedPeopleCounterService;

import com.fiedormichal.epidemicsimulation.model.CalculationData;
import com.fiedormichal.epidemicsimulation.model.SingleDaySimulation;
import com.fiedormichal.epidemicsimulation.repository.SingleDaySimulationRepository;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.junit4.SpringRunner;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

@RunWith(SpringRunner.class)
@SpringBootTest
class InfectedPeopleCounterServiceImplTest {

    @Autowired
    private InfectedPeopleCounterService infectedPeopleCounterService;

    @MockBean
    private SingleDaySimulationRepository singleDaySimulationRepository;

    @Test
    void should_set_positive_value_of_infected_people_when_days_iterator_is_two() throws Exception {
        //given
        SingleDaySimulation currentSimulationDay = new SingleDaySimulation();
        currentSimulationDay.setNumberOfDeathPeople(1);
        currentSimulationDay.setNumberOfPeopleWhoRecoveredAndGainedImmunity(9);
        CalculationData calculationData = CalculationData.builder()
                .peopleInfectedByOnePerson(1.6)
                .build();
        long iterator = 2;
        SingleDaySimulation singleDaySimulationForLastRecordId = mock(SingleDaySimulation.class);
        when(singleDaySimulationRepository.findFirstByOrderByIdDesc()).thenReturn(singleDaySimulationForLastRecordId);
        when(singleDaySimulationForLastRecordId.getId()).thenReturn(1L);
        SingleDaySimulation previousSimulationDay = new SingleDaySimulation();
        previousSimulationDay.setId(1);
        previousSimulationDay.setNumberOfInfectedPeople(3010);
        when(singleDaySimulationRepository.findById(1L)).thenReturn(java.util.Optional.of(previousSimulationDay));
        //when
        infectedPeopleCounterService.countInfectedPeopleBeforeParamReachedNumberOfPopulation(currentSimulationDay, calculationData, iterator);
        //then
        assertNotNull(currentSimulationDay);
        assertEquals((3010 * 1.6) + 3010 - 1 - 9, currentSimulationDay.getNumberOfInfectedPeople());
    }


    @Test
    void countInfectedPeopleBeforeParamReachedNumberOfPopulation() {
        //given
        SingleDaySimulation currentSimulationDay = new SingleDaySimulation();
        CalculationData calculationData = CalculationData.builder()
                .build();
        long iterator = 0;
        SingleDaySimulation singleDaySimulationForLastRecordId = new SingleDaySimulation();
        singleDaySimulationForLastRecordId.setId(14L);
        SingleDaySimulation previousSimulationDay = new SingleDaySimulation();
        previousSimulationDay.setId(14L);
        SingleDaySimulation twoDaysPreviousCurrentDaySimulation = new SingleDaySimulation();
        twoDaysPreviousCurrentDaySimulation.setId(13L);
        when(singleDaySimulationRepository.findFirstByOrderByIdDesc()).thenReturn(singleDaySimulationForLastRecordId);
        when(singleDaySimulationForLastRecordId.getId()).thenReturn(14L);
        when(singleDaySimulationRepository.findById(14L)).thenReturn(java.util.Optional.of(previousSimulationDay));
        when(singleDaySimulationRepository.findById(13L)).thenReturn(java.util.Optional.of(twoDaysPreviousCurrentDaySimulation));
        //in progress
    }

    @Test
    void countInfectedPeopleWhenParamExceedNumberOfPopulation() {
    }

    @Test
    void countInfectedPeopleWhenParamReachedMaxValueForSimulation() {
    }
}