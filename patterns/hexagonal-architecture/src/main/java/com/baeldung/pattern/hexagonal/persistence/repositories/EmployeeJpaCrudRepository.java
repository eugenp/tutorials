package com.baeldung.pattern.hexagonal.persistence.repositories;

import com.baeldung.pattern.hexagonal.persistence.entities.EmployeeEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import javax.transaction.Transactional;

@Transactional
@Repository
public interface EmployeeJpaCrudRepository extends JpaRepository<EmployeeEntity, Long> {
}
