package org.baeldung.spring.persistence.dao.impl;

import org.baeldung.spring.persistence.dao.IParentDao;
import org.baeldung.spring.persistence.dao.common.AbstractHibernateDao;
import org.baeldung.spring.persistence.model.Parent;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

@Repository
public class ParentDao extends AbstractHibernateDao<Parent> implements IParentDao {

    @Autowired
    private SessionFactory sessionFactory;

    public ParentDao() {
        super();

        setClazz(Parent.class);
    }

    // API

}
