package com.fiedormichal.epidemicsimulation.controller;

import com.fiedormichal.epidemicsimulation.dto.InitialSimulationDataDto;
import com.fiedormichal.epidemicsimulation.dto.InitialSimulationDataDtoMapper;
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
    public List<InitialSimulationDataDto> getAllInitialSimulationData() {
        return InitialSimulationDataDtoMapper.mapToInitialSimulationDataDtos(initialSimulationDataService.findAll());
    }

    @GetMapping("/initialdata/simulations")
    public List<InitialSimulationData> getAllInitialSimulationDataWithSimulations(){
        return initialSimulationDataService.findAll();
    }

    @GetMapping("/initialdata/{id}")
    public InitialSimulationDataDto getInitialSimulationDataById(@PathVariable long id) {
        return InitialSimulationDataDtoMapper.mapToInitialSimulationDataDto(initialSimulationDataService.findById(id));
    }

    @PostMapping("/initialdata")
    public InitialSimulationData addSimulations(@RequestBody InitialSimulationData initialSimulationData) {

        return initialSimulationDataService.addSimulations(initialSimulationData);
    }

    @PutMapping("/initialdata")
    public InitialSimulationData editInitialSimulationData(@RequestBody InitialSimulationData initialSimulationData){
        return initialSimulationDataService.edit(initialSimulationData);
    }

    @DeleteMapping("/initialdata/{id}")
    public void deleteInitialData(@PathVariable long id){
        initialSimulationDataService.deleteById(id);
    }
}
