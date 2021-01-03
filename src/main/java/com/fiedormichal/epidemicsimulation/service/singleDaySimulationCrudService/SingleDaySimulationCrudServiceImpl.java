package com.fiedormichal.epidemicsimulation.service.singleDaySimulationCrudService;

import com.fiedormichal.epidemicsimulation.model.SingleDaySimulation;
import com.fiedormichal.epidemicsimulation.repository.SingleDaySimulationRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@RequiredArgsConstructor
@Service
public class SingleDaySimulationCrudServiceImpl implements SingleDaySimulationCrudService {
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
    public List<SingleDaySimulation> findAll() {
        return singleDaySimulationRepository.findAll();
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
