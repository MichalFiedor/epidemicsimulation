package com.fiedormichal.epidemicsimulation.controller;

import com.fiedormichal.epidemicsimulation.dto.InitialSimulationDataDto;
import com.fiedormichal.epidemicsimulation.dto.InitialSimulationDataDtoMapper;
import com.fiedormichal.epidemicsimulation.model.InitialSimulationData;
import com.fiedormichal.epidemicsimulation.service.InitialSimulationDataCrudService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;


@RestController
@RequiredArgsConstructor
public class InitialSimulationDataController {
    private final InitialSimulationDataCrudService initialSimulationDataService;

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
    public ResponseEntity<Object> addSimulations(@RequestBody InitialSimulationData initialSimulationData) {
        InitialSimulationData initialSimulationDataWithSimulation =
                initialSimulationDataService.addInitialDataAndGenerateSimulation(initialSimulationData);
        return ResponseEntity.ok().body(initialSimulationDataWithSimulation);
    }

    @PutMapping("/initialdata")
    public InitialSimulationData editInitialSimulationData(@RequestBody InitialSimulationData initialSimulationData){
        try {
            return initialSimulationDataService.edit(initialSimulationData);
        } catch (Exception exception) {
            exception.printStackTrace();
        }
        return new InitialSimulationData();
    }

    @DeleteMapping("/initialdata/{id}")
    @ResponseStatus(value = HttpStatus.NO_CONTENT)
    public void deleteInitialData(@PathVariable long id){
        initialSimulationDataService.deleteById(id);
    }
}
