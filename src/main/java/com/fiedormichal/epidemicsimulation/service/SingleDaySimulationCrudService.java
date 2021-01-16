package com.fiedormichal.epidemicsimulation.service;

import com.fiedormichal.epidemicsimulation.model.SingleDaySimulation;
import com.fiedormichal.epidemicsimulation.repository.SingleDaySimulationRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@RequiredArgsConstructor
@Service
public class SingleDaySimulationCrudService {
    private final SingleDaySimulationRepository singleDaySimulationRepository;

    public SingleDaySimulation findById(long id) {
        return singleDaySimulationRepository.findById(id).orElseThrow();
    }
}
