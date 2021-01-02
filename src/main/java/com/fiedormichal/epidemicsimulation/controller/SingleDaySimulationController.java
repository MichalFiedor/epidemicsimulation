package com.fiedormichal.epidemicsimulation.controller;

import com.fiedormichal.epidemicsimulation.model.SingleDaySimulation;
import com.fiedormichal.epidemicsimulation.repository.SingleDaySimulationRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequiredArgsConstructor
public class SingleDaySimulationController {
    private final SingleDaySimulationRepository singleDaySimulationRepository;
    @GetMapping("/initialdata/{id}/simulations")
    public List<SingleDaySimulation> getSimulationsForInitialSimulationData(@PathVariable long id) {
        return singleDaySimulationRepository.findAllByInitialSimulationDataId(id);
    }
}
