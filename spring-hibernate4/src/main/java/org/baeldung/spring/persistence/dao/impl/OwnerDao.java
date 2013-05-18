package org.baeldung.spring.persistence.dao.impl;

import org.baeldung.spring.persistence.dao.IOwnerDao;
import org.baeldung.spring.persistence.dao.common.AbstractHibernateDao;
import org.baeldung.spring.persistence.model.Owner;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

@Repository
public class OwnerDao extends AbstractHibernateDao<Owner> implements IOwnerDao {

    @Autowired
    private SessionFactory sessionFactory;

    public OwnerDao() {
        super();

        setClazz(Owner.class);
    }

    // API

}
