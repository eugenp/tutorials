package com.baeldung.pattern.hexagonal.domain.ports;

import com.baeldung.pattern.hexagonal.domain.models.Employee;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import java.util.Optional;

public interface EmployeeRepository {

    Employee add(@NotNull @Valid Employee employee) throws RepositoryException;

    Optional<Employee> findById(long id) throws RepositoryException;

}
