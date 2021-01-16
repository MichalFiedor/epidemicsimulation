package com.fiedormichal.epidemicsimulation.repository;

import com.fiedormichal.epidemicsimulation.model.SingleDaySimulation;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface SingleDaySimulationRepository extends JpaRepository<SingleDaySimulation, Long> {

    SingleDaySimulation findFirstByOrderByIdDesc();

    @Query(value = "SELECT * FROM single_day_simulation WHERE initial_simulation_data_id=?1", nativeQuery = true)
    List<SingleDaySimulation> findAllSimulationsForInitialData(long initialDataId);

}
