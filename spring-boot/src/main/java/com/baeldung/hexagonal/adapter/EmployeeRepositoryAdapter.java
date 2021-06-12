package com.baeldung.hexagonal.adapter;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import com.baeldung.hexagonal.domain.Employee;
import com.baeldung.hexagonal.port.EmployeeRepositoryPort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;


@Service
public class EmployeeRepositoryAdapter implements EmployeeRepositoryPort {

    @PersistenceContext
    private EntityManager entityManager;
    
    @Transactional
    @Override
    public boolean create(String name, String role, long salary) {
        Employee employee = new Employee();
        employee.setName(name);
        employee.setRole(role);
        employee.setSalary(salary);
        entityManager.persist(employee);
        return true;
    }

    @Override
    public Employee getEmployee(Integer userId) {
        return entityManager.find(Employee.class, userId);
    }
}