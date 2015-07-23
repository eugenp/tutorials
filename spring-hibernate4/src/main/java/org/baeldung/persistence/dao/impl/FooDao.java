package org.baeldung.persistence.dao.impl;

import org.baeldung.persistence.dao.IFooDao;
import org.baeldung.persistence.dao.common.AbstractHibernateDao;
import org.baeldung.persistence.model.Foo;
import org.springframework.stereotype.Repository;

@Repository
public class FooDao extends AbstractHibernateDao<Foo>implements IFooDao {

    public FooDao() {
        super();

        setClazz(Foo.class);
    }

    // API

}
