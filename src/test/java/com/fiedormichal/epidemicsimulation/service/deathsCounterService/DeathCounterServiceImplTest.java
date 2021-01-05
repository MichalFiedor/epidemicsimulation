package com.fiedormichal.epidemicsimulation.service.deathsCounterService;

import com.fiedormichal.epidemicsimulation.EpidemicSimulationApplication;
import com.fiedormichal.epidemicsimulation.model.CalculationData;
import com.fiedormichal.epidemicsimulation.model.SingleDaySimulation;
import com.fiedormichal.epidemicsimulation.repository.SingleDaySimulationRepository;
import com.fiedormichal.epidemicsimulation.service.calculationDataService.CalculationDataServiceImpl;
import org.junit.Before;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.MockitoJUnitRunner;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@RunWith(MockitoJUnitRunner.class)
@SpringBootTest
class DeathCounterServiceImplTest {

    @Autowired
    private DeathsCounterService deathsCounterService;

    @Test
    void shouldSetOneHundredDeathPeople() {
        SingleDaySimulationRepository singleDaySimulationRepository= mock(SingleDaySimulationRepository.class);
        SingleDaySimulation currentSimulation = new SingleDaySimulation();
        currentSimulation.setId(15);
        SingleDaySimulation singleDaySimulationById = mock(SingleDaySimulation.class);
        when(singleDaySimulationRepository.findFirstByOrderByIdDesc()).thenReturn(singleDaySimulationById);
        when(singleDaySimulationById.getId()).thenReturn(14L);
        SingleDaySimulation singleDaySimulation = new SingleDaySimulation();
        singleDaySimulation.setId(2L);
        singleDaySimulation.setNumberOfInfectedPeople(5000);
        Optional<SingleDaySimulation> singleDaySimulationOpt = Optional.of(singleDaySimulation);
        CalculationData calculationData = CalculationData.builder()
                .daysFromInfectionToDeath(14)
                .mortalityRate(0.02)
                .build();
        when(singleDaySimulationRepository.findById(2L)).thenReturn(singleDaySimulationOpt);
        //when
        deathsCounterService.countDeathPeople(currentSimulation, calculationData);
        //then
        assertEquals(5000*0.02, currentSimulation.getNumberOfInfectedPeople());
    }
}