package com.baeldung.ddd.hexagonal.architecture.output.adapter;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.baeldung.ddd.hexagonal.architecture.domain.Employee;
import com.baeldung.ddd.hexagonal.architecture.output.port.EmployeeDatabaseService;
import com.baeldung.ddd.hexagonal.architecture.respository.EmployeeRepository;

@Repository
public class EmployeeDatabaseServiceImpl implements EmployeeDatabaseService{

    EmployeeRepository empRepository;
    
    @Autowired
    public EmployeeDatabaseServiceImpl(EmployeeRepository empRepository) {
        this.empRepository = empRepository;
    }
    
    @Override
    public String saveEmployee(Employee e) {
        empRepository.save(e);
        return e.getId();
    }

    @Override
    public Employee getEmployee(String id) {
        Optional<Employee> employee =  empRepository.findById(id);
        return employee.get();
    }

    @Override
    public void deleteEmployee(String id) {
        empRepository.deleteById(id);
    }

}
