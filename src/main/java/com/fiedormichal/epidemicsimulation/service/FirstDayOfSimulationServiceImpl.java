package com.fiedormichal.epidemicsimulation.service;

import com.fiedormichal.epidemicsimulation.model.InitialSimulationData;
import com.fiedormichal.epidemicsimulation.model.SingleDaySimulation;
import com.fiedormichal.epidemicsimulation.repository.SingleDaySimulationRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class FirstDayOfSimulationServiceImpl implements FirstDayOfSimulationService{
    private final SingleDaySimulationRepository singleDaySimulationRepository;

    @Override
    public SingleDaySimulation createFirstDayOfSimulation(InitialSimulationData initialSimulationData) {
        SingleDaySimulation firstDayOfSimulation = new SingleDaySimulation();

        firstDayOfSimulation.setNumberOfInfectedPeople(
                initialSimulationData.getInitialNumberOfInfected());

        firstDayOfSimulation.setNumberOfHealthyPeopleWhoCanBeInfected(
                initialSimulationData.getPopulationSize() - initialSimulationData.getInitialNumberOfInfected());

        firstDayOfSimulation.setNumberOfDeathPeople(0);

        firstDayOfSimulation.setNumberOfPeopleWhoRecoveredAndGainedImmunity(0);
        firstDayOfSimulation.setInitialSimulationDataId(initialSimulationData.getId());
        singleDaySimulationRepository.save(firstDayOfSimulation);

        return firstDayOfSimulation;
    }
}
