package org.baeldung.persistence.dao;

import org.baeldung.persistence.model.Foo;
import org.springframework.stereotype.Repository;

@Repository
class FooDao extends AbstractJpaDAO<Foo> implements IFooDao {

    public FooDao() {
        super();

        setClazz(Foo.class);
    }

    // API

}
