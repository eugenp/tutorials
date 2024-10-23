package com.baeldung.toggle;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class SalaryService {

    @Autowired
    EmployeeRepository employeeRepository;

    @FeatureAssociation(value = MyFeatures.EMPLOYEE_MANAGEMENT_FEATURE)
    public void increaseSalary(long id) {
        Employee employee = employeeRepository.findById(id).orElse(null);
        employee.setSalary(employee.getSalary() + employee.getSalary() * 0.1);
        employeeRepository.save(employee);
    }

}
