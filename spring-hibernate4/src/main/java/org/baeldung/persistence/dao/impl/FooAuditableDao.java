package org.baeldung.persistence.dao.impl;

import org.baeldung.persistence.dao.IFooAuditableDao;
import org.baeldung.persistence.dao.common.AbstractHibernateAuditableDao;
import org.baeldung.persistence.model.Foo;

public class FooAuditableDao extends AbstractHibernateAuditableDao<Foo> implements IFooAuditableDao {

    public FooAuditableDao() {
        super();

        setClazz(Foo.class);
    }

    // API

}
