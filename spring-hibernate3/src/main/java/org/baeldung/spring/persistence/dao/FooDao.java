package org.baeldung.spring.persistence.dao;

import org.baeldung.spring.persistence.model.Foo;
import org.springframework.stereotype.Repository;

@Repository
public class FooDao extends AbstractHibernateDao<Foo> implements IFooDao {

    public FooDao() {
        super();

        setClazz(Foo.class);
    }

    // API

}
