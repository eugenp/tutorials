package org.baeldung.persistence.dao.impl;

import org.baeldung.persistence.common.AbstractHibernateDao;
import org.baeldung.persistence.dao.IParentDao;
import org.baeldung.persistence.model.Parent;
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
