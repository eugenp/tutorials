package com.baeldung.hibernate.manytomany.dao.impl;

import org.springframework.stereotype.Repository;

import com.baeldung.hibernate.manytomany.dao.IEmployeeDao;
import com.baeldung.hibernate.manytomany.dao.common.AbstractHibernateDao;
import com.baeldung.hibernate.manytomany.model.Employee;

@Repository
public class EmployeeDao extends AbstractHibernateDao<Employee> implements IEmployeeDao {

    public EmployeeDao() {
        super();

        setClazz(Employee.class);
    }
}
