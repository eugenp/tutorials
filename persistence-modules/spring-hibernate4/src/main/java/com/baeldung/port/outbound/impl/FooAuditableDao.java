package com.baeldung.port.outbound.impl;

import com.baeldung.port.outbound.common.AbstractHibernateAuditableDao;
import com.baeldung.port.model.Foo;
import com.baeldung.port.outbound.IFooAuditableDao;

public class FooAuditableDao extends AbstractHibernateAuditableDao<Foo> implements IFooAuditableDao {

    public FooAuditableDao() {
        super();

        setClazz(Foo.class);
    }

    // API

}
