package com.bealdung.onboarding.application.service;

import com.bealdung.onboarding.adapters.persistence.EmployeeEntity;
import com.bealdung.onboarding.adapters.persistence.EmployeeRepository;
import com.bealdung.onboarding.application.domain.Employee;
import com.bealdung.onboarding.application.port.input.EmployeeNameAndFamily;
import com.bealdung.onboarding.application.port.output.FindEmployeeInterface;
import org.springframework.stereotype.Service;

@Service
public class EmployeeService implements EmployeeNameAndFamily, FindEmployeeInterface {
    private EmployeeRepository employeeRepository;

    public EmployeeService(EmployeeRepository employeeRepository) {
        this.employeeRepository = employeeRepository;
    }

    @Override
    public String getNameAndFamily(Long id) {
        Employee employeeProfile = new Employee();
        if (id != null) {
            EmployeeEntity employee = findEmployee(id);
            employeeProfile.setName(employee.getName());
            employeeProfile.setFamily(employee.getFamily());
            employeeProfile.setId(employee.getId());
            return employeeProfile.getEmployeeInfo();
        } else return "";
    }

    @Override
    public EmployeeEntity findEmployee(Long id) {
        return employeeRepository.findById(id).get();
    }
}
