package com.baeldung.port.manytomany.dao.impl;

import org.springframework.stereotype.Repository;
import com.baeldung.hibernate.manytomany.model.Employee;
import com.baeldung.port.dao.common.AbstractHibernateDao;
import com.baeldung.port.manytomany.dao.IEmployeeDao;

@Repository
public class EmployeeDao extends AbstractHibernateDao<Employee> implements IEmployeeDao {

    public EmployeeDao() {
        super();

        setClazz(Employee.class);
    }
}
