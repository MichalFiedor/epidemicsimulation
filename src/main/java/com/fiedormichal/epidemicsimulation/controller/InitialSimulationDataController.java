package com.fiedormichal.epidemicsimulation.controller;

import com.fiedormichal.epidemicsimulation.model.InitialSimulationData;
import com.fiedormichal.epidemicsimulation.service.InitialSimulationDataService;
import com.fiedormichal.epidemicsimulation.service.SingleDaySimulationCalculationService;
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
    private final SingleDaySimulationCalculationService singleDaySimulationCalculationService;

    @GetMapping("/initialdata")
    public List<InitialSimulationData> allInitialSimulationData(){
        InitialSimulationData isd = new InitialSimulationData();
        isd.setSimulationName("Covid");
        isd.setHowManyPeopleWillBeInfectedByOnePerson(1.4);
        isd.setInitialNumberOfInfected(100);
        isd.setMortalityRate(.1);
        isd.setNumberOfSimulationDays(50);
        isd.setPopulationSize(2000000);
        isd.setDaysFromInfectionToDeath(15);
        isd.setDaysFromInfectionToRecovery(14);
        initialSimulationDataService.save(isd);
        singleDaySimulationCalculationService.calculateEverySimulationDay(isd);

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
