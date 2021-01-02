package com.fiedormichal.epidemicsimulation.repository;

import com.fiedormichal.epidemicsimulation.model.SingleDaySimulation;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;


@Repository
public interface SingleDaySimulationRepository extends JpaRepository<SingleDaySimulation, Long> {

    SingleDaySimulation findFirstByOrderByIdDesc();
    List<SingleDaySimulation> findAllByInitialSimulationDataId(long initialDataId);

}
