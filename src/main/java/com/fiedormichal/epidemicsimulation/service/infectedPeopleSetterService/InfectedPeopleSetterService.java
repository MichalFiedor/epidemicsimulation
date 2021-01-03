package com.fiedormichal.epidemicsimulation.service.infectedPeopleSetterService;

import com.fiedormichal.epidemicsimulation.model.CalculationData;
import com.fiedormichal.epidemicsimulation.model.SingleDaySimulation;

public interface InfectedPeopleSetterService {
    void changeMethodForCountingAsNeededAndSetValue(SingleDaySimulation singleDaySimulation, CalculationData calculationData, long iterator);

    void setZero(SingleDaySimulation singleDaySimulation, CalculationData calculationData);
}