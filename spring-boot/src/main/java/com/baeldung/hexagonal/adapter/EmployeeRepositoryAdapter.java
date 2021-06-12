package com.baeldung.adapter;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import com.baeldung.domain.Employee;
import com.baeldung.port.EmployeeRepositoryPort;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;


@Service
public class EmployeeRepositoryAdapter implements EmployeeRepositoryPort {

    @PersistenceContext
    private EntityManager entityManager;
    
    @Transactional
    @Override
    public void create(String name, String role, long salary) {
        Employee employee = new Employee();
        employee.setName(name);
        employee.setRole(role);
        employee.setSalary(salary);
       
        entityManager.persist(employee);
    }

    @Override
    public Employee getEmployee(Integer userId) {
        return entityManager.find(Employee.class, userId);
    }
}