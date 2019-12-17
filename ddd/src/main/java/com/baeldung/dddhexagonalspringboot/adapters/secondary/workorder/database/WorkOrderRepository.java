package com.baeldung.dddhexagonalspringboot.adapters.secondary.workorder.database;

import org.springframework.context.annotation.Profile;
import org.springframework.data.jpa.repository.JpaRepository;

@Profile("database")
public interface WorkOrderRepository extends JpaRepository<WorkOrderDB, Long> {

}
