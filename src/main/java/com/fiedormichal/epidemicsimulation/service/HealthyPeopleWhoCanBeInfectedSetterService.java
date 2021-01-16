package com.fiedormichal.epidemicsimulation.service;

import com.fiedormichal.epidemicsimulation.model.CalculationData;
import com.fiedormichal.epidemicsimulation.model.SingleDaySimulation;
import com.fiedormichal.epidemicsimulation.service.healthyPeopleWhoCanBeInfectedCounterService.HealthyPeopleWhoCanBeInfectedCounterService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class HealthyPeopleWhoCanBeInfectedSetterService{
    private final HealthyPeopleWhoCanBeInfectedCounterService healthyPeopleWhoCanBeInfectedCounterService;

    public void setTotalNumberOfHealthyPeopleWhoCanBeInfectedForSingleSimulationDay(SingleDaySimulation currentSimulationDay,
                                                                                    CalculationData calculationData) {
        if (calculationData.isShouldSetZeroForNumberOfHealthyPeopleWhoCanBeInfected()) {
            currentSimulationDay.setNumberOfHealthyPeopleWhoCanBeInfected(0);
        } else {
            healthyPeopleWhoCanBeInfectedCounterService.countHealthyPeopleWhoCanBeInfected(currentSimulationDay, calculationData);
        }
        if (calculationData.isSetConstantValueOfPeopleWhoCanBeInfected()) {
            currentSimulationDay.setNumberOfHealthyPeopleWhoCanBeInfected(calculationData.getMinValueOfPeopleWhoCanBeInfectedForSimulation());
        }
    }
}
