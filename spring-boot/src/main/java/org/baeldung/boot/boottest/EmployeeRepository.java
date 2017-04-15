package org.baeldung.boot.boottest;

import java.util.List;
import java.util.Optional;

import javax.transaction.Transactional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
@Transactional
public interface EmployeeRepository extends JpaRepository<Employee, Long> {

    public Optional<Employee> findByName(String name);

    public Optional<Employee> findById(Long id);

    public List<Employee> findAll();

}
