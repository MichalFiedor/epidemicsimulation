package com.fiedormichal.epidemicsimulation.service.infectedPeopleSetterService;

import com.fiedormichal.epidemicsimulation.model.CalculationData;
import com.fiedormichal.epidemicsimulation.model.SingleDaySimulation;
import com.fiedormichal.epidemicsimulation.repository.SingleDaySimulationRepository;
import com.fiedormichal.epidemicsimulation.service.InfectedPeopleSetterService;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

@RunWith(SpringRunner.class)
@SpringBootTest
class InfectedPeopleSetterServiceImplTest {

    @Autowired
    private InfectedPeopleSetterService infectedPeopleSetterService;

    @MockBean
    private SingleDaySimulationRepository singleDaySimulationRepository;

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
                .shouldSetZeroForNumberOfInfectedPeople(true)
                .build();
        //when
        infectedPeopleSetterService.setZero(currentSimulationDay, calculationData);
        //then
        assertNotNull(currentSimulationDay);
        assertEquals(0, currentSimulationDay.getNumberOfInfectedPeople());
    }

    @Test
    void should_be_changed_method_for_counting_when_max_value_occurs_and_set_correct_value_of_infected_people() {
        //given
        SingleDaySimulation currentSimulationDay = new SingleDaySimulation();
        currentSimulationDay.setNumberOfDeathPeople(45);
        currentSimulationDay.setNumberOfPeopleWhoRecoveredAndGainedImmunity(430);
        CalculationData calculationData = CalculationData.builder()
                .shouldChangeMethodForCountingNumberOfInfectedPeopleWhenMaxValueOccurs(true)
                .maxValueOfInfectedPeopleForAllSimulation(3500)
                .daysFromInfectionToRecovery(14)
                .counterFromStartOfTheSimulationToOccursMaxValueOfInfectedPeopleForSimulation(9)
                .build();
        int iterator= 14;
        //when
        infectedPeopleSetterService.changeMethodForCountingAsNeededAndSetValue(currentSimulationDay, calculationData, iterator);
        //then
        assertNotNull(currentSimulationDay);
        assertEquals(3500-45-430, currentSimulationDay.getNumberOfInfectedPeople());
    }

    @Test
    void should_be_changed_method_for_counting_when_max_value_occurs_and_set_zero_as_value_of_infected_people() {
        //given
        SingleDaySimulation currentSimulationDay = new SingleDaySimulation();
        CalculationData calculationData = CalculationData.builder()
                .shouldChangeMethodForCountingNumberOfInfectedPeopleWhenMaxValueOccurs(true)
                .daysFromInfectionToRecovery(14)
                .counterFromStartOfTheSimulationToOccursMaxValueOfInfectedPeopleForSimulation(7)
                .build();
        int iterator= 22;
        //when
        infectedPeopleSetterService.changeMethodForCountingAsNeededAndSetValue(currentSimulationDay, calculationData, iterator);
        //then
        assertNotNull(currentSimulationDay);
        assertEquals(0, currentSimulationDay.getNumberOfInfectedPeople());
    }

    @Test
    void should_be_changed_method_for_counting_when_param_exceed_number_of_population_and_set_correct_value_of_infected_people() {
        //given
        SingleDaySimulation currentSimulationDay = new SingleDaySimulation();
        currentSimulationDay.setNumberOfDeathPeople(57);
        currentSimulationDay.setNumberOfPeopleWhoRecoveredAndGainedImmunity(875);
        CalculationData calculationData = CalculationData.builder()
                .shouldChangeMethodForCountingNumberOfInfectedPeopleWhenParamExceedNumberOfPopulation(true)
                .population(3500)
                .build();
        int iterator= 16;
        //when
        infectedPeopleSetterService.changeMethodForCountingAsNeededAndSetValue(currentSimulationDay, calculationData, iterator);
        //then
        assertNotNull(currentSimulationDay);
        assertEquals(3500-875-57, currentSimulationDay.getNumberOfInfectedPeople());
    }

    @Test
    void should_be_changed_method_for_counting_when_param_is_lower_than_number_of_population_and_set_correct_value_of_infected_people() {
        //given
        SingleDaySimulation currentSimulationDay = new SingleDaySimulation();
        currentSimulationDay.setNumberOfDeathPeople(0);
        currentSimulationDay.setNumberOfPeopleWhoRecoveredAndGainedImmunity(75);
        SingleDaySimulation previousSimulationDay = new SingleDaySimulation();
        previousSimulationDay.setId(19L);
        previousSimulationDay.setNumberOfInfectedPeople(2150);
        SingleDaySimulation twoDaysPreviousCurrentDaySimulation = new SingleDaySimulation();
        twoDaysPreviousCurrentDaySimulation.setId(18L);
        twoDaysPreviousCurrentDaySimulation.setNumberOfInfectedPeople(1350);
        SingleDaySimulation simulationDayFromCurrentSimulationDayMinusPeriodBetweenInfectionAndRecovery = new SingleDaySimulation();
        simulationDayFromCurrentSimulationDayMinusPeriodBetweenInfectionAndRecovery.setId(6L);
        simulationDayFromCurrentSimulationDayMinusPeriodBetweenInfectionAndRecovery.setNumberOfInfectedPeople(5000);
        List<SingleDaySimulation> singleDaySimulations = new ArrayList<>();
        for(int i=0; i< 16; i++){
            singleDaySimulations.add(new SingleDaySimulation());
        }
        singleDaySimulations.add(twoDaysPreviousCurrentDaySimulation);
        singleDaySimulations.add(previousSimulationDay);
        singleDaySimulations.add(5, simulationDayFromCurrentSimulationDayMinusPeriodBetweenInfectionAndRecovery);
        CalculationData calculationData = CalculationData.builder()
                .peopleInfectedByOnePerson(1.5)
                .daysFromInfectionToRecovery(14)
                .mortalityRate(0.03)
                .singleDaySimulationsListForInitialData(singleDaySimulations)
                .build();
        int iterator = 19;

        //when
        infectedPeopleSetterService.changeMethodForCountingAsNeededAndSetValue(currentSimulationDay, calculationData, iterator);
        //then
        assertNotNull(currentSimulationDay);
        assertEquals((2150-1350)*1.5 + 2150 + (5000*0.03), currentSimulationDay.getNumberOfInfectedPeople());
    }
}