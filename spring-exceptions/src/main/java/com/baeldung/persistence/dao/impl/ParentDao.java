package com.baeldung.persistence.dao.impl;

import com.baeldung.persistence.common.AbstractHibernateDao;
import com.baeldung.persistence.model.Parent;
import com.baeldung.persistence.dao.IParentDao;
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
