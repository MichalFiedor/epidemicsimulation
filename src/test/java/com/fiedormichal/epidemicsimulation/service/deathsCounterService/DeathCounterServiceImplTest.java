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

import java.util.Arrays;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@RunWith(SpringRunner.class)
@SpringBootTest
class DeathCounterServiceImplTest {

    @Autowired
    private DeathsCounterService deathsCounterService;

    @Test
    public void should_set_one_hundred_death_people() {
        //given
        SingleDaySimulation currentSimulation = new SingleDaySimulation();
        currentSimulation.setId(1L);
        SingleDaySimulation singleDaySimulation = new SingleDaySimulation();
        singleDaySimulation.setId(2L);
        singleDaySimulation.setNumberOfInfectedPeople(5000);
        CalculationData calculationData = CalculationData.builder()
                .daysFromInfectionToDeath(14)
                .mortalityRate(0.02)
                .singleDaySimulationsListForInitialData(Arrays.asList(singleDaySimulation))
                .build();
        //when
        deathsCounterService.countDeathPeople(currentSimulation, calculationData, 14);
        //then
        assertNotNull(currentSimulation);
        assertEquals(5000*0.02, currentSimulation.getNumberOfDeathPeople());
    }
}