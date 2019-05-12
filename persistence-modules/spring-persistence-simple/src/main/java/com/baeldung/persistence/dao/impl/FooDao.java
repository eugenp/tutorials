package com.baeldung.persistence.dao.impl;

import org.baeldung.persistence.dao.IFooDao;
import org.baeldung.persistence.model.Foo;
import org.springframework.stereotype.Repository;

import com.baeldung.persistence.dao.common.AbstractHibernateDao;

@Repository
public class FooDao extends AbstractHibernateDao<Foo> implements IFooDao {

    public FooDao() {
        super();

        setClazz(Foo.class);
    }

    // API

}
