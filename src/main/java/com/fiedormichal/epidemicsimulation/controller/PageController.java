package com.fiedormichal.epidemicsimulation.controller;

import com.fiedormichal.epidemicsimulation.model.InitialSimulationData;
import com.fiedormichal.epidemicsimulation.service.InitialSimulationDataCrudService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;


import javax.validation.Valid;

@Controller
@RequiredArgsConstructor
public class PageController {
    private final InitialSimulationDataCrudService initialSimulationDataService;

    @GetMapping("/")
    public String showForm(Model model){
        model.addAttribute("initialData", new InitialSimulationData());
        return "index";
    }
}
