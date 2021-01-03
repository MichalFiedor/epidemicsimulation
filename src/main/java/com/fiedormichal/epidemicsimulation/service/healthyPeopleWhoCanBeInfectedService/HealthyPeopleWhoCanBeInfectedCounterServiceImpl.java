package com.fiedormichal.epidemicsimulation.service.healthyPeopleWhoCanBeInfectedService;

import com.fiedormichal.epidemicsimulation.model.CalculationData;
import com.fiedormichal.epidemicsimulation.model.SingleDaySimulation;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class HealthyPeopleWhoCanBeInfectedCounterServiceImpl implements HealthyPeopleWhoCanBeInfectedCounterService {
    @Override
    public void countHealthyPeopleWhoCanBeInfected(SingleDaySimulation currentSimulationDay, CalculationData calculationData) {
        long healthyPeopleWhoCanBeInfected = calculationData.getPopulation() - currentSimulationDay.getNumberOfInfectedPeople()
                - currentSimulationDay.getNumberOfDeathPeople() - currentSimulationDay.getNumberOfPeopleWhoRecoveredAndGainedImmunity();
        if (healthyPeopleWhoCanBeInfected < 0) {
            currentSimulationDay.setNumberOfHealthyPeopleWhoCanBeInfected(0);
        }else {
            currentSimulationDay.setNumberOfHealthyPeopleWhoCanBeInfected(healthyPeopleWhoCanBeInfected);
        }
    }
}
