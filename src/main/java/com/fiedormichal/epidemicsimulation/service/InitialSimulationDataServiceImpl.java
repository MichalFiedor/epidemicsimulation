package com.fiedormichal.epidemicsimulation.service;

import com.fiedormichal.epidemicsimulation.model.InitialSimulationData;
import com.fiedormichal.epidemicsimulation.repository.InitialSimulationDataRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@RequiredArgsConstructor
@Service
public class InitialSimulationDataServiceImpl implements InitialSimulationDataService{
    private final InitialSimulationDataRepository initialSimulationDataRepository;

    @Override
    public InitialSimulationData save(InitialSimulationData initialSimulationData) {
        return initialSimulationDataRepository.save(initialSimulationData);
    }

    @Override
    public InitialSimulationData findById(long id) {
        return initialSimulationDataRepository.findById(id).orElseThrow();
    }

    @Override
    public List<InitialSimulationData> findAll() {
        return initialSimulationDataRepository.findAll();
    }

    @Override
    public InitialSimulationData edit(InitialSimulationData initialSimulationData) {
        return initialSimulationDataRepository.save(initialSimulationData);
    }

    @Override
    public void deleteById(long id) {
        initialSimulationDataRepository.deleteById(id);
    }
}
