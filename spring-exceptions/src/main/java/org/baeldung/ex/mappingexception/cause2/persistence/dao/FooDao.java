package org.baeldung.ex.mappingexception.cause2.persistence.dao;

import org.baeldung.ex.mappingexception.cause2.persistence.model.Foo;
import org.baeldung.persistence.common.AbstractHibernateDao;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

@Repository
public class FooDao extends AbstractHibernateDao<Foo> implements IFooDao {

    @Autowired
    private SessionFactory sessionFactory;

    public FooDao() {
        super();

        setClazz(Foo.class);
    }

    // API

}
