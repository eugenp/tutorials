package com.bealdung.onboarding.application.service;
import com.bealdung.onboarding.adapters.persistence.EmployeeEntity;
import com.bealdung.onboarding.adapters.persistence.EmployeeRepository;
import com.bealdung.onboarding.application.domain.Employee;
import com.bealdung.onboarding.application.port.input.EmployeeNameAndFamilyPort;
import com.bealdung.onboarding.application.port.output.FindEmployeePort;
import org.springframework.stereotype.Service;
@Service
public class EmployeeServicePort implements EmployeeNameAndFamilyPort, FindEmployeePort {
    private EmployeeRepository employeeRepository;
    public EmployeeServicePort(EmployeeRepository employeeRepository) {
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
