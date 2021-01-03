package com.fiedormichal.epidemicsimulation.service.healthyPeopleWhoCanBeInfectedSetterService;

import com.fiedormichal.epidemicsimulation.model.CalculationData;
import com.fiedormichal.epidemicsimulation.model.SingleDaySimulation;
import com.fiedormichal.epidemicsimulation.service.healthyPeopleWhoCanBeInfectedService.HealthyPeopleWhoCanBeInfectedCounterService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class HealthyPeopleWhoCanBeInfectedSetterServiceImpl implements HealthyPeopleWhoCanBeInfectedSetterService {
    private final HealthyPeopleWhoCanBeInfectedCounterService healthyPeopleWhoCanBeInfectedCounterService;

    @Override
    public void setTotalNumberOfHealthyPeopleWhoCanBeInfectedForSingleSimulationDay(SingleDaySimulation singleDaySimulation,
                                                                                    CalculationData calculationData) {
        if (calculationData.isShouldSetZeroForNumberHealthyPeopleWhoCanBeInfected()) {
            singleDaySimulation.setNumberOfHealthyPeopleWhoCanBeInfected(0);
        } else {
            healthyPeopleWhoCanBeInfectedCounterService.countHealthyPeopleWhoCanBeInfected(singleDaySimulation, calculationData);
        }
        if (calculationData.isSetConstantValueOfPeopleWhoCanBeInfected()) {
            singleDaySimulation.setNumberOfHealthyPeopleWhoCanBeInfected(calculationData.getMinValueOfPeopleWhoCanBeInfected());
        }
    }
}
