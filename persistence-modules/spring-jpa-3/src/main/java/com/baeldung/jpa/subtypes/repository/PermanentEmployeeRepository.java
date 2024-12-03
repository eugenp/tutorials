package com.baeldung.jpa.subtypes.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.baeldung.jpa.subtypes.entity.PermanentEmployee;

public interface PermanentEmployeeRepository extends JpaRepository<PermanentEmployee, Long> {
}
