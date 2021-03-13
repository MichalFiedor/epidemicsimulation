package com.fiedormichal.epidemicsimulation.controller;

import com.fiedormichal.epidemicsimulation.service.InitialSimulationDataCrudService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;


@Controller
@RequiredArgsConstructor
public class PageController {
    private final InitialSimulationDataCrudService initialSimulationDataService;

    @GetMapping("/")
    public String showForm(){
        return "index";
    }

    @GetMapping("/charts")
    public String showCharts(){
        return "charts";
    }
}
