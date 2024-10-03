package com.baeldung.boot.testing;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Repository
@Transactional
public interface EmployeeRepository extends JpaRepository<Employee, Long> {

    public Employee findByName(String name);

    public List<Employee> findAll();

}
