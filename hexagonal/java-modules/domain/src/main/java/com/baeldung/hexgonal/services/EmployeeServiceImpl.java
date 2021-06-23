package com.baeldung.hexgonal.services;


import com.baeldung.hexgonal.data.Employee;
import com.baeldung.hexgonal.repo.EmployeeRepository;

public class EmployeeServiceImpl implements EmployeeService {

    private final EmployeeRepository employeeRepository;

    public EmployeeServiceImpl(EmployeeRepository employeeRepository) {
        this.employeeRepository = employeeRepository;
    }

    @Override
    public Long createEmployee(Long id , String firstName , String lastName , String code) {
        final Employee employee = new Employee(id,firstName,lastName,code);
        employeeRepository.save(employee);
        return employee.getId();
    }

    @Override
    public void deleteEmployee(Long id) {
        employeeRepository.delete(id);
    }

}
