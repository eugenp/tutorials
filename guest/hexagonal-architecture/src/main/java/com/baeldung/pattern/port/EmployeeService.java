package com.baeldung.pattern.port;

import org.springframework.stereotype.Service;

import com.baeldung.pattern.domain.Employee;

@Service
public interface EmployeeService {

    Employee getEmployee(String employeeId);

}
