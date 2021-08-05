package com.baeldung.pattern.hexagonal.domain.ports;

import com.baeldung.pattern.hexagonal.domain.models.Employee;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.util.Optional;

/**
 * The port that can be used by user/command line/application interfaces to use the domain.
 */
public interface EmployeeService {

    Employee addEmployee(@Valid Employee employee) throws ServiceException;

    Employee getEmployee(long employeeId) throws ServiceException, NotFoundException;

}
