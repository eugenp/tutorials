package com.baeldung.examples.r2dbc.flyway.repository;

import java.util.UUID;

import org.springframework.data.repository.reactive.ReactiveCrudRepository;

import com.baeldung.examples.r2dbc.flyway.model.Student;

public interface StudentRepository extends ReactiveCrudRepository<Student, UUID> {
}
