package com.baeldung.spring.hibernate;

import org.springframework.stereotype.Repository;

@Repository
public class FooDao extends AbstractHibernateDao<Foo> implements IFooDao {
    public FooDao() {
        super();

        setClazz(Foo.class);
    }

    // API
}
