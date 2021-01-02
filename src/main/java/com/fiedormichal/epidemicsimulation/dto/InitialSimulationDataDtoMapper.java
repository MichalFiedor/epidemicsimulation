package com.fiedormichal.epidemicsimulation.dto;

import com.fiedormichal.epidemicsimulation.model.InitialSimulationData;

import java.util.List;
import java.util.stream.Collectors;

public class InitialSimulationDataDtoMapper {

    private InitialSimulationDataDtoMapper(){

    }

    public static List<InitialSimulationDataDto> mapToInitialSimulationDataDtos(List<InitialSimulationData> initialSimulationDataList){
        return initialSimulationDataList.stream()
                .map(initialSimulationData -> mapToInitialSimulationDataDto(initialSimulationData))
                .collect(Collectors.toList());
    }

    public static InitialSimulationDataDto mapToInitialSimulationDataDto(InitialSimulationData initialSimulationData) {
        return InitialSimulationDataDto.builder()
                .id(initialSimulationData.getId())
                .simulationName(initialSimulationData.getSimulationName())
                .populationSize(initialSimulationData.getPopulationSize())
                .initialNumberOfInfected(initialSimulationData.getInitialNumberOfInfected())
                .numberOfPeopleWhoWillBeInfectedByOnePerson(initialSimulationData.getNumberOfPeopleWhoWillBeInfectedByOnePerson())
                .mortalityRate(initialSimulationData.getMortalityRate())
                .daysFromInfectionToRecovery(initialSimulationData.getDaysFromInfectionToRecovery())
                .daysFromInfectionToDeath(initialSimulationData.getDaysFromInfectionToDeath())
                .numberOfSimulationDays(initialSimulationData.getNumberOfSimulationDays())
                .build();
    }
}
