package com.baeldung.hexagonal.architecture.infrastructure.jpa.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import com.baeldung.hexagonal.architecture.infrastructure.jpa.entities.StudentEntity;

public interface StudentRepository extends JpaRepository<StudentEntity, Integer> {

}
