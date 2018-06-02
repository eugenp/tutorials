package com.baeldung.spring.data.keyvalue.services.impl;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.baeldung.spring.data.keyvalue.repositories.EmployeeRepository;
import com.baeldung.spring.data.keyvalue.services.EmployeeService;
import com.baeldung.spring.data.keyvalue.vo.Employee;

@Service("employeeServicesWithRepository")
public class EmployeeServicesWithRepository implements EmployeeService {

	@Autowired
    EmployeeRepository employeeRepository;


    @Override
    public void save(Employee employee) {
        employeeRepository.save(employee);
    }

    @Override
    public Iterable<Employee> fetchAll() {
        return employeeRepository.findAll();

    }

    @Override
    public Optional<Employee> get(Integer id) {
        return employeeRepository.findById(id);
    }

    @Override
    public void update(Employee employee) {
        employeeRepository.save(employee);

    }

    @Override
    public void delete(Integer id) {
        employeeRepository.deleteById(id);
    }

    public Iterable<Employee> getSortedListOfEmployeesBySalary() {
        throw new RuntimeException("Method not supported by CRUDRepository");
    }

}
