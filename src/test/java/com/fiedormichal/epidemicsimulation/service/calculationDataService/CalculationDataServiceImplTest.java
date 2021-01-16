package com.fiedormichal.epidemicsimulation.service.calculationDataService;

import com.fiedormichal.epidemicsimulation.model.CalculationData;
import com.fiedormichal.epidemicsimulation.model.InitialSimulationData;
import com.fiedormichal.epidemicsimulation.service.CalculationDataService;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import static org.junit.jupiter.api.Assertions.*;

@RunWith(SpringRunner.class)
@SpringBootTest
class CalculationDataServiceImplTest {

    @Autowired
    private CalculationDataService calculationDataService;

    @Test
    void should_return_calculation_data_object() {
        //given
        InitialSimulationData initialSimulationData = new InitialSimulationData();
        initialSimulationData.setDaysFromInfectionToRecovery(14);
        initialSimulationData.setDaysFromInfectionToDeath(18);
        initialSimulationData.setMortalityRate(0.04);
        initialSimulationData.setPopulationSize(200000);
        initialSimulationData.setNumberOfSimulationDays(50);
        //when
        CalculationData result = calculationDataService.createCalculationDataObject(initialSimulationData);
        //then
        assertNotNull(result);
        assertEquals(14, result.getDaysFromInfectionToRecovery());
        assertEquals(18, result.getDaysFromInfectionToDeath());
        assertEquals(0.04, result.getMortalityRate());
        assertEquals(200000, result.getPopulation());
        assertEquals(0, result.getNumberOfDaysWhenAmountOfInfectedPeopleGrowsToExceedNumOfPopulation());
        assertEquals(0, result.getCounterFromStartOfTheSimulationToOccursMaxValueOfInfectedPeopleForSimulation());
        assertFalse(result.isSetConstantValueOfPeopleWhoCanBeInfected());
        assertFalse(result.isShouldChangeMethodForCountingNumberOfInfectedPeopleWhenParamExceedNumberOfPopulation());
        assertFalse(result.isShouldChangeMethodForCountingNumberOfInfectedPeopleWhenMaxValueOccurs());
        assertFalse(result.isShouldSetZeroForNumberOfHealthyPeopleWhoCanBeInfected());
        assertFalse(result.isShouldSetZeroForNumberOfInfectedPeople());
        assertFalse(result.isShouldSetZeroForNumberOfRecoveredPeople());
    }
}