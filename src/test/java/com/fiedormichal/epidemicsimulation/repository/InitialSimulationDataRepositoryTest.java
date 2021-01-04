package com.fiedormichal.epidemicsimulation.repository;

import com.fiedormichal.epidemicsimulation.model.InitialSimulationData;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.*;


@RunWith(SpringRunner.class)
@DataJpaTest
class InitialSimulationDataRepositoryTest {

    @Autowired
    private TestEntityManager entityManager;

    @Autowired
    private InitialSimulationDataRepository initialSimulationDataRepository;

    @Test
    void shouldReturnNotDeletedInitialSimulationDataObject() {
        //given
        InitialSimulationData initialSimulationData = new InitialSimulationData();
        initialSimulationData.setDeleted(false);
        entityManager.persist(initialSimulationData);
        //when
        InitialSimulationData result = initialSimulationDataRepository.findAllNotDeleted().get(0);
        //then
        assertNotNull(result);
        assertEquals(initialSimulationData, result);
    }

    @Test
    void shouldReturnListOfNotDeletedInitialSimulationDataObjects() {
        //given
        List<InitialSimulationData> simulationData= new ArrayList<>();
        InitialSimulationData initialSimulationData1 = new InitialSimulationData();
        initialSimulationData1.setDeleted(false);
        entityManager.persist(initialSimulationData1);
        InitialSimulationData initialSimulationData2 = new InitialSimulationData();
        initialSimulationData2.setDeleted(false);
        entityManager.persist(initialSimulationData2);
        InitialSimulationData initialSimulationData3 = new InitialSimulationData();
        initialSimulationData3.setDeleted(false);
        entityManager.persist(initialSimulationData3);
        InitialSimulationData initialSimulationData4 = new InitialSimulationData();
        initialSimulationData4.setDeleted(true);
        entityManager.persist(initialSimulationData4);
        //when
        List<InitialSimulationData> result = initialSimulationDataRepository.findAllNotDeleted();
        //then
        assertNotNull(result);
        assertEquals(3, result.size());
        assertEquals(simulationData.getClass(), result.getClass());
    }

    @Test
    void shouldReturnEmptyList() {
        //given
        InitialSimulationData initialSimulationData1 = new InitialSimulationData();
        initialSimulationData1.setDeleted(true);
        entityManager.persist(initialSimulationData1);
        InitialSimulationData initialSimulationData2 = new InitialSimulationData();
        initialSimulationData2.setDeleted(true);
        entityManager.persist(initialSimulationData2);
        InitialSimulationData initialSimulationData3 = new InitialSimulationData();
        initialSimulationData3.setDeleted(true);
        entityManager.persist(initialSimulationData3);
        //when
        List<InitialSimulationData> result = initialSimulationDataRepository.findAllNotDeleted();
        //then
        assertNotNull(result);
        assertEquals(0, result.size());
    }
}