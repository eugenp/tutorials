package com.baeldung.template.service;

import com.baeldung.template.domain.Employee;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface EmployeeService {

    List<Employee> getEmployeeList();
}
