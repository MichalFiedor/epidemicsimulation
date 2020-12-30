package com.fiedormichal.epidemicsimulation.controller;

import com.fiedormichal.epidemicsimulation.model.InitialSimulationData;
import com.fiedormichal.epidemicsimulation.service.InitialSimulationDataService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;


@RestController
@RequiredArgsConstructor
public class InitialSimulationDataController {
    private final InitialSimulationDataService initialSimulationDataService;

    @GetMapping("/initialdata")
    public List<InitialSimulationData> allInitialSimulationData(){
        InitialSimulationData isd = new InitialSimulationData();
        isd.setSimulationName("Covid");
        isd.setHowManyPeopleWillBeInfectedByOnePerson(1.4);
        isd.setInitialNumberOfInfected(1000);
        isd.setMortalityRate(4);
        isd.setNumberOfSimulationDays(100);
        isd.setPopulationSize(20000);
        initialSimulationDataService.save(isd);

        return initialSimulationDataService.findAll();
    }

    @GetMapping("/initialdata/{id}")
    public InitialSimulationData findInitialSimulationDataById(@PathVariable long id){
        return initialSimulationDataService.findById(id);
    }

    @PostMapping("/initialdata")
    public InitialSimulationData addInitialSimulationData(InitialSimulationData initialSimulationData){
        return initialSimulationDataService.save(initialSimulationData);
    }
}
