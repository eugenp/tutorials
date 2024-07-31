package com.baeldung.examples.r2dbc.flyway.repository;

import java.util.UUID;

import org.springframework.data.repository.reactive.ReactiveCrudRepository;

import com.baeldung.examples.r2dbc.flyway.model.Department;

public interface DepartmentRepository extends ReactiveCrudRepository<Department, UUID> {
}
