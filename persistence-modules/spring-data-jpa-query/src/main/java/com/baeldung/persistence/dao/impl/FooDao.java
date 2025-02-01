package com.baeldung.persistence.dao.impl;

import org.springframework.stereotype.Repository;

import com.baeldung.persistence.dao.IFooDao;
import com.baeldung.persistence.dao.common.AbstractHibernateDao;
import com.baeldung.persistence.model.Foo;

@Repository
public class FooDao extends AbstractHibernateDao<Foo> implements IFooDao {

    public FooDao() {
        super();

        setClazz(Foo.class);
    }

    // API

}
