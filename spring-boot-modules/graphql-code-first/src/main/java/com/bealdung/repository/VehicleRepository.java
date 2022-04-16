package com.bealdung.graphqlcodefirst.repository;

import com.bealdung.graphqlcodefirst.entity.Vehicle;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface VehicleRepository extends JpaRepository<Vehicle, Integer> {
}
