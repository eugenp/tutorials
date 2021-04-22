package com.baeldung.pattern.domain;

import java.util.Optional;

import org.springframework.stereotype.Service;

import com.baeldung.pattern.port.EmployeeRepository;
import com.baeldung.pattern.port.EmployeeService;

import lombok.AllArgsConstructor;

@Service
@AllArgsConstructor
public class EmployeeServiceImpl implements EmployeeService {

    private final EmployeeRepository employeeRepository;

    @Override
    public Employee getEmployee(String employeeId) {
        Optional<Employee> maybeEmployee = employeeRepository.findById(employeeId);

        if (maybeEmployee.isPresent()) {
            Employee employee = maybeEmployee.get();
            employee.calculateBonus();
            return employee;
        } else {
            return null;
        }
    }

}
