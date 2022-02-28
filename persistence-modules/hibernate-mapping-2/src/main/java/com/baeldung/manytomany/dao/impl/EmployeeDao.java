package com.baeldung.manytomany.dao.impl;

import org.springframework.stereotype.Repository;

import com.baeldung.manytomany.dao.IEmployeeDao;
import com.baeldung.manytomany.dao.common.AbstractHibernateDao;
import com.baeldung.manytomany.model.Employee;

@Repository
public class EmployeeDao extends AbstractHibernateDao<Employee> implements IEmployeeDao {

    public EmployeeDao() {
        super();

        setClazz(Employee.class);
    }
}
