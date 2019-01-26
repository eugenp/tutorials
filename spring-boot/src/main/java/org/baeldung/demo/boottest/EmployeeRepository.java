package org.baeldung.demo.boottest;

import java.util.List;

import javax.transaction.Transactional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
@Transactional
public interface EmployeeRepository extends JpaRepository<Employee, Long> {

    public Employee findByName(String name);

    public Employee findById(Long id);

    public List<Employee> findAll();

}
