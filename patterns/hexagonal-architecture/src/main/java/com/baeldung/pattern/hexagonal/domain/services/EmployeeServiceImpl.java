package com.baeldung.pattern.hexagonal.domain.services;

import com.baeldung.pattern.hexagonal.domain.models.Employee;
import com.baeldung.pattern.hexagonal.domain.ports.EmployeeRepository;
import com.baeldung.pattern.hexagonal.domain.ports.EmployeeService;
import com.baeldung.pattern.hexagonal.domain.ports.NotFoundException;
import com.baeldung.pattern.hexagonal.domain.ports.RepositoryException;
import com.baeldung.pattern.hexagonal.domain.ports.ServiceException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class EmployeeServiceImpl implements EmployeeService {

    // this is the port
    private final EmployeeRepository employeeRepository;

    @Override
    public Employee addEmployee(Employee employee) throws ServiceException {
        try {
            // you could add further business logic here
            return employeeRepository.add(employee);
        } catch (RepositoryException e) {
            throw new ServiceException(e);
        }
    }

    @Override
    public Employee getEmployee(long employeeId) throws ServiceException, NotFoundException {
        try {
            // you could add further business logic here
            return employeeRepository.findById(employeeId).orElseThrow(NotFoundException::new);
        } catch (RepositoryException e) {
            throw new ServiceException(e);
        }
    }
}
