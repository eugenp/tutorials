package com.baeldung.hexagonal.domain;


import java.util.List;

import com.baeldung.hexagonal.output.EmployeeOutput;
import com.baeldung.hexagonal.storage.EmployeeRepository;

public class EmployeeService {
   private EmployeeRepository employeeRepository;
   private EmployeeOutput employeeOutput;
   
   public EmployeeService(EmployeeRepository employeeRepository, EmployeeOutput employeeOutput) {
       this.employeeRepository = employeeRepository;
       this.employeeOutput = employeeOutput;
   }
   
   public Long add(Employee employee) {
       return employeeRepository.save(employee);
   }
   
   public void generateOutput() {
       List<Employee> employees = employeeRepository.findAll();
       employeeOutput.writeAll(employees);
   }
}
