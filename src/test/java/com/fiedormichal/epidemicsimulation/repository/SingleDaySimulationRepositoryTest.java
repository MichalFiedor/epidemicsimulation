package com.fiedormichal.epidemicsimulation.repository;

import com.fiedormichal.epidemicsimulation.model.InitialSimulationData;
import com.fiedormichal.epidemicsimulation.model.SingleDaySimulation;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@RunWith(SpringRunner.class)
@DataJpaTest
class SingleDaySimulationRepositoryTest {

    @Autowired
    private TestEntityManager entityManager;

    @Autowired
    private SingleDaySimulationRepository singleDaySimulationRepository;

    @Test
    void shouldReturnOneSimulationForInitialSimulationData() {
        //given
        InitialSimulationData initialSimulationData = new InitialSimulationData();
        SingleDaySimulation singleDaySimulation= new SingleDaySimulation();
        entityManager.persist(singleDaySimulation);
        List<SingleDaySimulation> singleDaySimulationsForInitialData = Arrays.asList(singleDaySimulation);
        initialSimulationData.setSingleDaySimulations(singleDaySimulationsForInitialData);
        entityManager.persist(initialSimulationData);
        //when
        SingleDaySimulation result = singleDaySimulationRepository.findAllSimulationsForInitialData(1).get(0);
        //then
        assertNotNull(result);
        assertEquals(singleDaySimulation, result);
    }

    @Test
    void shouldReturnListOfSimulationsForInitialSimulationData() {
        //given
        InitialSimulationData initialSimulationData1 = new InitialSimulationData();
        entityManager.persist(initialSimulationData1);

        InitialSimulationData initialSimulationData2 = new InitialSimulationData();

        SingleDaySimulation singleDaySimulation1 = new SingleDaySimulation();
        entityManager.persist(singleDaySimulation1);
        SingleDaySimulation singleDaySimulation2 = new SingleDaySimulation();
        entityManager.persist(singleDaySimulation2);
        SingleDaySimulation singleDaySimulation3 = new SingleDaySimulation();
        entityManager.persist(singleDaySimulation3);
        List<SingleDaySimulation> singleDaySimulationsForInitialData = new ArrayList<>();
        singleDaySimulationsForInitialData.add(singleDaySimulation1);
        singleDaySimulationsForInitialData.add(singleDaySimulation2);
        singleDaySimulationsForInitialData.add(singleDaySimulation3);
        initialSimulationData2.setSingleDaySimulations(singleDaySimulationsForInitialData);
        entityManager.persist(initialSimulationData2);
        //when
        List<SingleDaySimulation> result = singleDaySimulationRepository.findAllSimulationsForInitialData(2);
        //then
        assertNotNull(result);
        assertEquals(3, result.size());
        assertEquals(singleDaySimulationsForInitialData.getClass(), result.getClass());
        assertEquals(singleDaySimulation1, result.get(0));
        assertEquals(singleDaySimulation2, result.get(1));
        assertEquals(singleDaySimulation3, result.get(2));
    }

    @Test
    void shouldReturnEmptyListOfSimulationsForInitialSimulationDataWhenGivenCorrectId() {
        //given
        InitialSimulationData initialSimulationData1 = new InitialSimulationData();
        entityManager.persist(initialSimulationData1);
        List<SingleDaySimulation> singleDaySimulationsForInitialData = new ArrayList<>();
        //when
        List<SingleDaySimulation> result = singleDaySimulationRepository.findAllSimulationsForInitialData(1);
        //then
        assertNotNull(result);
        assertEquals(0, result.size());
        assertEquals(singleDaySimulationsForInitialData.getClass(), result.getClass());
    }

    @Test
    void shouldReturnEmptyListOfSimulationsForInitialSimulationDataWhenGivenNotCorrectId() {
        //when
        List<SingleDaySimulation> result = singleDaySimulationRepository.findAllSimulationsForInitialData(2);
        //then
        assertNotNull(result);
        assertEquals(0, result.size());
    }
}