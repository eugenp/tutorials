package com.baeldung.hibernate.manyToMany.usage;

import org.springframework.stereotype.Repository;
import com.baeldung.hibernate.manyToMany.model.Employee;
import com.baeldung.persistence.dao.common.AbstractHibernateDao;

@Repository
public class EmployeeDao extends AbstractHibernateDao<Employee> implements IEmployeeDao {

    public EmployeeDao() {
        super();

        setClazz(Employee.class);
    }
}
