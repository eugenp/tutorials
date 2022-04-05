package com.baeldung.persistence.dao.impl;

import com.baeldung.persistence.common.AbstractHibernateDao;
import com.baeldung.persistence.model.Child;
import com.baeldung.persistence.dao.IChildDao;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

@Repository
public class ChildDao extends AbstractHibernateDao<Child> implements IChildDao {

    @Autowired
    private SessionFactory sessionFactory;

    public ChildDao() {
        super();

        setClazz(Child.class);
    }

    // API

}
