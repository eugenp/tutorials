package com.baeldung.persistence.manytomany.dao.impl;

import org.springframework.stereotype.Repository;
import com.baeldung.hibernate.manytomany.model.Employee;
import com.baeldung.persistence.dao.common.AbstractHibernateDao;
import com.baeldung.persistence.manytomany.dao.IEmployeeDao;

@Repository
public class EmployeeDao extends AbstractHibernateDao<Employee> implements IEmployeeDao {

    public EmployeeDao() {
        super();

        setClazz(Employee.class);
    }
}
