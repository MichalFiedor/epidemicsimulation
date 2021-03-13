package com.fiedormichal.epidemicsimulation.controller;

import com.fiedormichal.epidemicsimulation.dto.InitialSimulationDataDto;
import com.fiedormichal.epidemicsimulation.dto.InitialSimulationDataDtoMapper;
import com.fiedormichal.epidemicsimulation.model.InitialSimulationData;
import com.fiedormichal.epidemicsimulation.service.InitialSimulationDataCrudService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@CrossOrigin("*")
@RestController
@RequiredArgsConstructor
public class InitialSimulationDataController {
    private final InitialSimulationDataCrudService initialSimulationDataService;

    @GetMapping("/initialdata")
    public ResponseEntity<List<InitialSimulationDataDto>> getAllInitialSimulationData() {
        List<InitialSimulationDataDto>listOfInitialSimulationData =
                InitialSimulationDataDtoMapper.mapToInitialSimulationDataDtos(initialSimulationDataService.findAll());
        return ResponseEntity.ok().body(listOfInitialSimulationData);
    }

    @GetMapping("/initialdata/simulations")
    public ResponseEntity<List<InitialSimulationData>> getAllInitialSimulationDataWithSimulations(){
        return ResponseEntity.ok().body(initialSimulationDataService.findAll());
    }

    @GetMapping("/initialdata/{id}")
    public ResponseEntity<InitialSimulationDataDto> getInitialSimulationDataById(@PathVariable long id) {
        InitialSimulationDataDto initialSimulationDataDto = InitialSimulationDataDtoMapper
                .mapToInitialSimulationDataDto(initialSimulationDataService.findById(id));
        return ResponseEntity.ok().body(initialSimulationDataDto);
    }

    @PostMapping("/initialdata")
    public ResponseEntity<Object> addSimulations(@Valid @RequestBody InitialSimulationData initialSimulationData) {
        InitialSimulationData initialSimulationDataWithSimulation =
                initialSimulationDataService.addInitialDataAndGenerateSimulation(initialSimulationData);
        return ResponseEntity.ok().body(initialSimulationDataWithSimulation);
    }

    @PutMapping("/initialdata")
    public ResponseEntity<InitialSimulationData> editInitialSimulationData(@Valid @RequestBody InitialSimulationData initialSimulationData){
            return ResponseEntity.ok().body(initialSimulationDataService.edit(initialSimulationData));
    }

    @DeleteMapping("/initialdata/{id}")
    @ResponseStatus(value = HttpStatus.NO_CONTENT)
    public void deleteInitialData(@PathVariable long id){
        initialSimulationDataService.deleteById(id);
    }
}
