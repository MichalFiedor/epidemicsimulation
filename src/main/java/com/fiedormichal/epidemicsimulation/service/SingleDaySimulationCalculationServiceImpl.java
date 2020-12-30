package com.fiedormichal.epidemicsimulation.service;

import com.fiedormichal.epidemicsimulation.model.InitialSimulationData;
import com.fiedormichal.epidemicsimulation.model.SingleDaySimulation;
import com.fiedormichal.epidemicsimulation.repository.SingleDaySimulationRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class SingleDaySimulationCalculationServiceImpl implements SingleDaySimulationCalculationService {
    private final SingleDaySimulationRepository singleDaySimulationRepository;


    @Override
    public List<SingleDaySimulation> calculateEverySimulationDay(InitialSimulationData initialSimulationData) {
        SingleDaySimulation firstDayOfSimulation = createFirstDayOfSimulationBasedOnTheInitialData(initialSimulationData);
        List<SingleDaySimulation> allSimulationDays = new ArrayList<>();

        double howManyPeopleWillBeInfectedByOnePerson = initialSimulationData.getHowManyPeopleWillBeInfectedByOnePerson();
        double mortalityRate = initialSimulationData.getMortalityRate();
        int daysFromInfectionToRecovery = initialSimulationData.getDaysFromInfectionToRecovery();
        int daysFromInfectionToDeath = initialSimulationData.getDaysFromInfectionToDeath();
        int numberOfSimulationDays = initialSimulationData.getNumberOfSimulationDays();

        for(long i =2; i<=numberOfSimulationDays; i++){
            SingleDaySimulation singleDaySimulation = new SingleDaySimulation();
            countDeathPeopleAndReturnTheSameDayOfSimulation(singleDaySimulation,
                    mortalityRate, daysFromInfectionToDeath, i);
            singleDaySimulationRepository.save(singleDaySimulation);
        }

        return null;
    }

    private SingleDaySimulation createFirstDayOfSimulationBasedOnTheInitialData(InitialSimulationData initialSimulationData) {
        SingleDaySimulation firstDayOfSimulation= new SingleDaySimulation();

        firstDayOfSimulation.setNumberOfInfectedPeople(
                initialSimulationData.getInitialNumberOfInfected());

        firstDayOfSimulation.setNumberOfHealthyPeopleWhoCanBeInfected(
                initialSimulationData.getPopulationSize()-initialSimulationData.getInitialNumberOfInfected());

        firstDayOfSimulation.setNumberOfDeathPeople(0);

        firstDayOfSimulation.setNumberOfPeopleWhoRecoveredAndGainedImmunity(0);
        singleDaySimulationRepository.save(firstDayOfSimulation);

        return firstDayOfSimulation;
    }

    private static SingleDaySimulation countDeathPeopleAndReturnTheSameDayOfSimulation(SingleDaySimulation singleDaySimulation,
                                  double mortalityRate, int daysFromInfectionToDeath, long daysIterator){
        if(daysIterator<daysFromInfectionToDeath){
            singleDaySimulation.setNumberOfDeathPeople(0);
        } else {
            long numberOfDeathPeople = (long)Math.ceil(
                    singleDaySimulation.getNumberOfInfectedPeople()*mortalityRate);
            singleDaySimulation.setNumberOfDeathPeople(numberOfDeathPeople);
            singleDaySimulation.setNumberOfInfectedPeople(
                    singleDaySimulation.getNumberOfInfectedPeople()-numberOfDeathPeople);
        }
        return singleDaySimulation;
    }
}
