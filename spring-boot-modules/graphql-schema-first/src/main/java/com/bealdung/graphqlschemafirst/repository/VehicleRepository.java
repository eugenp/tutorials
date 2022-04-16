package com.bealdung.graphqlschemafirst.repository;

import com.bealdung.graphqlschemafirst.entity.Vehicle;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface VehicleRepository extends JpaRepository<Vehicle, Integer> {
}
