package com.fiedormichal.epidemicsimulation.service.singleDaySimulationCrudService;

import com.fiedormichal.epidemicsimulation.model.InitialSimulationData;
import com.fiedormichal.epidemicsimulation.model.SingleDaySimulation;

import java.util.List;

public interface SingleDaySimulationCrudService {
    SingleDaySimulation save(SingleDaySimulation SingleDataSimulation);
    SingleDaySimulation findById(long id);
    List<SingleDaySimulation> findAll();
    SingleDaySimulation edit(SingleDaySimulation SingleDaySimulation);
    void deleteById(long id);

}
