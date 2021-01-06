package com.fiedormichal.epidemicsimulation.service.deathsCounterService;

import com.fiedormichal.epidemicsimulation.model.CalculationData;
import com.fiedormichal.epidemicsimulation.model.SingleDaySimulation;
import com.fiedormichal.epidemicsimulation.repository.SingleDaySimulationRepository;
import org.junit.Rule;
import org.junit.jupiter.api.Test;
import org.junit.rules.ExpectedException;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@RunWith(SpringRunner.class)
@SpringBootTest
class DeathCounterServiceImplTest {

    @Autowired
    private DeathsCounterService deathsCounterService;

    @MockBean
    SingleDaySimulationRepository singleDaySimulationRepository;

    @Test
    public void should_set_one_hundred_death_people() {
        //given
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
        assertNotNull(currentSimulation);
        assertEquals(5000*0.02, currentSimulation.getNumberOfDeathPeople());
    }
}