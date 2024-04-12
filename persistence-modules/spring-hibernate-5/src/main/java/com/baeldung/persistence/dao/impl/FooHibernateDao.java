package com.baeldung.persistence.dao.impl;

import com.baeldung.persistence.dao.common.AbstractHibernateDao;
import com.baeldung.persistence.dao.IFooDao;
import com.baeldung.persistence.model.Foo;
import org.springframework.stereotype.Repository;

@Repository
public class FooHibernateDao extends AbstractHibernateDao<Foo> implements IFooDao {

    public FooHibernateDao() {
        super();

        setClazz(Foo.class);
    }

    // API

}
