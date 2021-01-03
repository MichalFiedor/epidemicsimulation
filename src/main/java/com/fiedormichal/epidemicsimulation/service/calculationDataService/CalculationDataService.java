package com.fiedormichal.epidemicsimulation.service.calculationDataService;

import com.fiedormichal.epidemicsimulation.model.CalculationData;
import com.fiedormichal.epidemicsimulation.model.InitialSimulationData;

public interface CalculationDataService {
    CalculationData createCalculationDataObject(InitialSimulationData initialSimulationData);
}
