package com.fiedormichal.epidemicsimulation.service;

import com.fiedormichal.epidemicsimulation.model.SingleDaySimulation;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class HealthyPeopleWhoCanBeInfectedSetterServiceImpl implements HealthyPeopleWhoCanBeInfectedSetterService {
    private final HealthyPeopleWhoCanBeInfectedCounterService healthyPeopleWhoCanBeInfectedCounterService;

    @Override
    public void setTotalNumberOfHealthyPeopleWhoCanBeInfectedForSingleSimulationDay(SingleDaySimulation singleDaySimulation,
                                                                                    boolean shouldSetZeroForNumberHealthPeopleWhoCanBeInfected,
                                                                                    boolean setConstantValueOfPeopleWhoCanBeInfected,
                                                                                    long minValueOfPeopleWhoCanBeInfected, long population) {
        if (shouldSetZeroForNumberHealthPeopleWhoCanBeInfected) {
            singleDaySimulation.setNumberOfHealthyPeopleWhoCanBeInfected(0);
        } else {
            healthyPeopleWhoCanBeInfectedCounterService.countHealthyPeopleWhoCanBeInfected(singleDaySimulation, population);
        }
        if (setConstantValueOfPeopleWhoCanBeInfected) {
            singleDaySimulation.setNumberOfHealthyPeopleWhoCanBeInfected(minValueOfPeopleWhoCanBeInfected);
        }
    }
}
