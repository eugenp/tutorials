package com.baeldung.employee.data;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.baeldung.employee.domain.Employee;
import com.baeldung.employee.dto.EmployeeDTO;

@Component
public class EmployeeDataAdapterImpl implements EmployeeDataAdapter {

    @PersistenceContext
    private EntityManager em;

    @Autowired
    private ModelMapper modelMapper;

    @Override
    public void addEmployee(EmployeeDTO emp) {

        em.persist(modelMapper.map(emp, Employee.class));

    }
}
