package org.baeldung.spring.persistence.dao.impl;

import org.baeldung.spring.persistence.dao.IChildDao;
import org.baeldung.spring.persistence.dao.common.AbstractHibernateDao;
import org.baeldung.spring.persistence.model.Child;
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
