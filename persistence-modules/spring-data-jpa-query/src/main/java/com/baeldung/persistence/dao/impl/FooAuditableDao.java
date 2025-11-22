package com.baeldung.persistence.dao.impl;

import com.baeldung.persistence.dao.IFooAuditableDao;
import com.baeldung.persistence.dao.common.AbstractHibernateAuditableDao;
import com.baeldung.persistence.model.Foo;

public class FooAuditableDao extends AbstractHibernateAuditableDao<Foo> implements IFooAuditableDao {

    public FooAuditableDao() {
        super();

        setClazz(Foo.class);
    }

}
