package com.fiedormichal.epidemicsimulation.repository;

import com.fiedormichal.epidemicsimulation.model.SingleDaySimulation;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface SingleDaySimulationRepository extends JpaRepository<SingleDaySimulation, Long> {
}
