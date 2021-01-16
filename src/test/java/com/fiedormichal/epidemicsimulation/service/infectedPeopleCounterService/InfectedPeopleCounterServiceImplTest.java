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

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

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
    void should_set_correct_value_of_infected_people_when_days_iterator_is_two() throws Exception {
        //given
        SingleDaySimulation currentSimulationDay = new SingleDaySimulation();
        currentSimulationDay.setNumberOfDeathPeople(1);
        currentSimulationDay.setNumberOfPeopleWhoRecoveredAndGainedImmunity(9);
        CalculationData calculationData = CalculationData.builder()
                .peopleInfectedByOnePerson(1.6)
                .build();
        int iterator = 2;
        SingleDaySimulation singleDaySimulationForLastRecordId = mock(SingleDaySimulation.class);
        when(singleDaySimulationRepository.findFirstByOrderByIdDesc()).thenReturn(singleDaySimulationForLastRecordId);
        when(singleDaySimulationForLastRecordId.getId()).thenReturn(1L);
        SingleDaySimulation previousSimulationDay = new SingleDaySimulation();
        previousSimulationDay.setId(1);
        previousSimulationDay.setNumberOfInfectedPeople(3010);
        when(singleDaySimulationRepository.findById(1L)).thenReturn(java.util.Optional.of(previousSimulationDay));
        //when
        infectedPeopleCounterService.countInfectedPeopleWhenParamIsLowerThanNumberOfPopulation(currentSimulationDay, calculationData, iterator);
        //then
        assertNotNull(currentSimulationDay);
        assertEquals((3010 * 1.6) + 3010 - 1 - 9, currentSimulationDay.getNumberOfInfectedPeople());
    }


    @Test
    void should_set_correct_value_of_infected_people_when_death_people_from_current_day_is_greater_than_zero() throws Exception {
        //given
        SingleDaySimulation currentSimulationDay = new SingleDaySimulation();
        currentSimulationDay.setNumberOfDeathPeople(5);
        currentSimulationDay.setNumberOfPeopleWhoRecoveredAndGainedImmunity(35);
        SingleDaySimulation previousSimulationDay = new SingleDaySimulation();
        previousSimulationDay.setId(15L);
        previousSimulationDay.setNumberOfInfectedPeople(1500);
        SingleDaySimulation twoDaysPreviousCurrentDaySimulation = new SingleDaySimulation();
        twoDaysPreviousCurrentDaySimulation.setId(14L);
        twoDaysPreviousCurrentDaySimulation.setNumberOfInfectedPeople(1150);
        CalculationData calculationData = CalculationData.builder()
                .peopleInfectedByOnePerson(1.5)
                .singleDaySimulationsListForInitialData(Arrays.asList(new SingleDaySimulation(), new SingleDaySimulation(),
                        twoDaysPreviousCurrentDaySimulation, previousSimulationDay))
                .build();
        int iterator = 4;
        //when
        infectedPeopleCounterService.countInfectedPeopleWhenParamIsLowerThanNumberOfPopulation(currentSimulationDay, calculationData, iterator);
        //then
        assertNotNull(currentSimulationDay);
        assertEquals((1500-1150)*1.5 + 1500 - 5 - 35, currentSimulationDay.getNumberOfInfectedPeople());
    }

    @Test
    void should_set_correct_value_of_infected_people_when_death_people_from_current_day_is_zero() throws Exception {
        //given
        SingleDaySimulation currentSimulationDay = new SingleDaySimulation();
        currentSimulationDay.setNumberOfDeathPeople(0);
        currentSimulationDay.setNumberOfPeopleWhoRecoveredAndGainedImmunity(75);
        SingleDaySimulation previousSimulationDay = new SingleDaySimulation();
        previousSimulationDay.setId(17L);
        previousSimulationDay.setNumberOfInfectedPeople(1500);
        SingleDaySimulation twoDaysPreviousCurrentDaySimulation = new SingleDaySimulation();
        twoDaysPreviousCurrentDaySimulation.setId(16L);
        twoDaysPreviousCurrentDaySimulation.setNumberOfInfectedPeople(1150);
        SingleDaySimulation simulationDayFromCurrentSimulationDayMinusPeriodBetweenInfectionAndRecovery = new SingleDaySimulation();
        simulationDayFromCurrentSimulationDayMinusPeriodBetweenInfectionAndRecovery.setId(5L);
        simulationDayFromCurrentSimulationDayMinusPeriodBetweenInfectionAndRecovery.setNumberOfInfectedPeople(50);
        List<SingleDaySimulation> singleDaySimulationsListForInitialData = new ArrayList<>();
        for(int i =0; i<15; i++){
            singleDaySimulationsListForInitialData.add(new SingleDaySimulation());
       }
        singleDaySimulationsListForInitialData.add(4, simulationDayFromCurrentSimulationDayMinusPeriodBetweenInfectionAndRecovery);
        singleDaySimulationsListForInitialData.add(twoDaysPreviousCurrentDaySimulation);
        singleDaySimulationsListForInitialData.add(previousSimulationDay);
        CalculationData calculationData = CalculationData.builder()
                .peopleInfectedByOnePerson(1.5)
                .daysFromInfectionToRecovery(14)
                .mortalityRate(0.06)
                .singleDaySimulationsListForInitialData(singleDaySimulationsListForInitialData)
                .build();
        int iterator = 18;

        //when
        infectedPeopleCounterService.countInfectedPeopleWhenParamIsLowerThanNumberOfPopulation(currentSimulationDay, calculationData, iterator);
        //then
        assertNotNull(currentSimulationDay);
        assertEquals((1500-1150)*1.5 + 1500 + (50*0.06), currentSimulationDay.getNumberOfInfectedPeople());
    }

    @Test
    public void should_set_correct_value_of_infected_people_when_infected_people_exceed_number_of_population(){
        //given
        SingleDaySimulation currentSimulationDay = new SingleDaySimulation();
        currentSimulationDay.setNumberOfDeathPeople(95);
        currentSimulationDay.setNumberOfPeopleWhoRecoveredAndGainedImmunity(3450);
        CalculationData calculationData = CalculationData.builder()
                .population(8000)
                .build();
        //when
        infectedPeopleCounterService.countInfectedPeopleWhenParamExceedNumberOfPopulation(currentSimulationDay, calculationData);
        //then
        assertNotNull(currentSimulationDay);
        assertEquals(8000-95-3450, currentSimulationDay.getNumberOfInfectedPeople());
    }

    @Test
    public void should_set_correct_value_of_infected_people_when_infected_people_reached_max_value_for_simulation(){
        //given
        SingleDaySimulation currentSimulationDay = new SingleDaySimulation();
        currentSimulationDay.setNumberOfDeathPeople(115);
        currentSimulationDay.setNumberOfPeopleWhoRecoveredAndGainedImmunity(2250);
        CalculationData calculationData = CalculationData.builder()
                .maxValueOfInfectedPeopleForAllSimulation(3640)
                .build();
        //when
        infectedPeopleCounterService.countInfectedPeopleWhenParamReachedMaxValueForSimulation(currentSimulationDay, calculationData);
        //then
        assertNotNull(currentSimulationDay);
        assertEquals(3640-115-2250, currentSimulationDay.getNumberOfInfectedPeople());
    }
}