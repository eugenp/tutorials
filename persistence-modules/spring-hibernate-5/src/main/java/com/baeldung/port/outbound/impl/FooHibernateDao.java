package com.baeldung.port.outbound.impl;

import com.baeldung.port.outbound.common.AbstractHibernateDao;
import com.baeldung.port.outbound.IFooDao;
import com.baeldung.port.model.Foo;
import org.springframework.stereotype.Repository;

@Repository
public class FooHibernateDao extends AbstractHibernateDao<Foo> implements IFooDao {

    public FooHibernateDao() {
        super();

        setClazz(Foo.class);
    }

    // API

}
