package com.fiedormichal.epidemicsimulation.controller;

import com.fiedormichal.epidemicsimulation.model.InitialSimulationData;
import com.fiedormichal.epidemicsimulation.model.SingleDaySimulation;
import com.fiedormichal.epidemicsimulation.service.InitialSimulationDataService;
import com.fiedormichal.epidemicsimulation.service.SingleDaySimulationCalculationService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;


@RestController
@RequiredArgsConstructor
public class InitialSimulationDataController {
    private final InitialSimulationDataService initialSimulationDataService;
    private final SingleDaySimulationCalculationService singleDaySimulationCalculationService;

    @GetMapping("/initialdata")
    public List<InitialSimulationData> allInitialSimulationData() {
        return initialSimulationDataService.findAll();
    }

    @GetMapping("/initialdata/{id}")
    public InitialSimulationData findInitialSimulationDataById(@PathVariable long id) {
        return initialSimulationDataService.findById(id);
    }

    @PostMapping("/initialdata")
    public InitialSimulationData addInitialSimulationData(@RequestBody InitialSimulationData initialSimulationData) {
        List<SingleDaySimulation> singleDaySimulations = singleDaySimulationCalculationService
                .calculateEverySimulationDay(initialSimulationData);
        return initialSimulationDataService.addSingleDaySimulations(singleDaySimulations, initialSimulationData);
    }
}
