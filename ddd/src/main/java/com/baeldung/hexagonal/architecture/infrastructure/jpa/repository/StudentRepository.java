package com.baeldung.hexagonal.architecture.infrastructure.jpa.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import com.baeldung.hexagonal.architecture.infrastructure.jpa.entity.StudentEntity;

public interface StudentRepository extends JpaRepository<StudentEntity, Integer> {

}
