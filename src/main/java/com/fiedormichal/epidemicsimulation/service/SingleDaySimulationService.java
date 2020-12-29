package com.fiedormichal.epidemicsimulation.service;

import com.fiedormichal.epidemicsimulation.model.SingleDaySimulation;

public interface SingleDaySimulationService {
    SingleDaySimulation save(SingleDaySimulation SingleDataSimulation);
    SingleDaySimulation findById(long id);
    SingleDaySimulation edit(SingleDaySimulation SingleDaySimulation);
    void deleteById(long id);
}
