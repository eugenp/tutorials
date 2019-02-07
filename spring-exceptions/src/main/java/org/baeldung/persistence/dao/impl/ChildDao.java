package org.baeldung.persistence.dao.impl;

import org.baeldung.persistence.common.AbstractHibernateDao;
import org.baeldung.persistence.dao.IChildDao;
import org.baeldung.persistence.model.Child;
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
