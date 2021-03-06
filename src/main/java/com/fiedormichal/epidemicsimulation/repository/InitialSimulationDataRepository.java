package com.fiedormichal.epidemicsimulation.repository;

import com.fiedormichal.epidemicsimulation.model.InitialSimulationData;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface InitialSimulationDataRepository extends JpaRepository<InitialSimulationData, Long> {

}
