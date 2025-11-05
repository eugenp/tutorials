package com.baeldung.spring.data.jpa.maxvalue;

import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.Comparator;
import java.util.Optional;

public interface EmployeeRepository extends JpaRepository<Employee, Integer> {
    Optional<Employee> findTopByOrderBySalaryDesc();

    Optional<EmployeeSalary> findTopSalaryByOrderBySalaryDesc();

    @Query("SELECT MAX(e.salary) FROM Employee e")
    Optional<Long> findTopSalaryJpql();

    @Query(value = "SELECT MAX(salary) FROM Employee", nativeQuery = true)
    Optional<Long> findTopSalaryNative();

    default Optional<Long> findTopSalaryCustomMethod() {
        return findAll().stream()
            .map(Employee::getSalary)
            .max(Comparator.naturalOrder());
    }

    default Optional<Long> findTopSalaryPageable() {
        return findAll(PageRequest.of(0, 1, Sort.by(Sort.Direction.DESC, "salary")))
            .stream()
            .map(Employee::getSalary)
            .findFirst();
    }
}
