package com.fiedormichal.epidemicsimulation.service.calculationDataService;

import com.fiedormichal.epidemicsimulation.model.CalculationData;
import com.fiedormichal.epidemicsimulation.model.InitialSimulationData;
import com.fiedormichal.epidemicsimulation.repository.InitialSimulationDataRepository;
import lombok.Data;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.stereotype.Controller;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.context.support.AnnotationConfigContextLoader;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;

@RunWith(SpringJUnit4ClassRunner.class)
class CalculationDataServiceImplTest {

    @Autowired
    private CalculationDataService calculationDataService;

//    @Test
//    void shouldReturnCalculationDataObject() {
//        //given
//        InitialSimulationData initialSimulationData = new InitialSimulationData();
//        initialSimulationData.setDaysFromInfectionToRecovery(14);
//        initialSimulationData.setDaysFromInfectionToDeath(18);
//        initialSimulationData.setMortalityRate(0.04);
//        initialSimulationData.setPopulationSize(200000);
//        initialSimulationData.setNumberOfSimulationDays(50);
//        CalculationData calculationData = CalculationData.builder()
//                .daysFromInfectionToRecovery(14)
//                .daysFromInfectionToDeath(18)
//                .mortalityRate(0.04)
//                .population(200000)
//                .numberOfSimulationDays(50)
//                .build();
////        when(calculationDataService.createCalculationDataObject(initialSimulationData)).thenReturn(calculationData);
//        //when
//        CalculationData result = calculationDataService.createCalculationDataObject(initialSimulationData);
//        //then
//        assertNotNull(result);
//        assertEquals(14, result.getDaysFromInfectionToRecovery());
//        assertEquals(18, result.getDaysFromInfectionToDeath());
//        assertEquals(0.04, result.getMortalityRate());
//        assertEquals(200000, result.getPopulation());
//        assertFalse(result.isSetConstantValueOfPeopleWhoCanBeInfected());
//        assertFalse(result.isShouldChangeMethodForCountingNumberOfInfectedPeople());
//        assertFalse(result.isShouldChangeMethodForCountingNumberOfInfectedPeopleWhenMaxValueOccurs());
//        assertFalse(result.isShouldSetZeroForNumberHealthyPeopleWhoCanBeInfected());
//        assertFalse(result.isShouldSetZeroForNumberInfectedPeople());
//        assertFalse(result.isShouldSetZeroForNumberRecoveredPeople());
//    }
}