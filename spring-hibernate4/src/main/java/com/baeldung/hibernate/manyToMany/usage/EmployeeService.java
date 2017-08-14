package com.baeldung.hibernate.manyToMany.usage;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.baeldung.hibernate.manyToMany.model.Employee;
import com.baeldung.persistence.dao.common.IOperations;
import com.baeldung.persistence.service.common.AbstractHibernateService;


@Service
public class EmployeeService extends AbstractHibernateService<Employee> implements IEmployeeService {

    @Autowired
    private IEmployeeDao dao;

    public EmployeeService() {
        super();
    }

    // API

    @Override
    protected IOperations<Employee> getDao() {
        return dao;
    }
}
