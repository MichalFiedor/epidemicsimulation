package com.fiedormichal.epidemicsimulation.service;

import com.fiedormichal.epidemicsimulation.model.SingleDaySimulation;
import com.fiedormichal.epidemicsimulation.repository.SingleDaySimulationRepository;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class SingleDaySimulationServiceImpl implements SingleDaySimulationService{
    private final SingleDaySimulationRepository singleDaySimulationRepository;

    @Override
    public SingleDaySimulation save(SingleDaySimulation singleDataSimulation) {
        return singleDaySimulationRepository.save(singleDataSimulation);
    }

    @Override
    public SingleDaySimulation findById(long id) {
        return singleDaySimulationRepository.findById(id).orElseThrow();
    }

    @Override
    public SingleDaySimulation edit(SingleDaySimulation singleDaySimulation) {
        return singleDaySimulationRepository.save(singleDaySimulation);
    }

    @Override
    public void deleteById(long id) {
        singleDaySimulationRepository.deleteById(id);
    }
}
