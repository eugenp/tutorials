package com.baeldung.graphql.error.handling.repository;

import com.baeldung.graphql.error.handling.domain.Vehicle;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface InventoryRepository extends JpaRepository<Vehicle, String>  {
}
