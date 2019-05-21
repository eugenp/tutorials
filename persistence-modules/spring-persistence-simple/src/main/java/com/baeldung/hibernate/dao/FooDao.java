package com.baeldung.hibernate.dao;

import com.baeldung.persistence.model.Foo;
import org.springframework.stereotype.Repository;

import com.baeldung.jpa.dao.IFooDao;
import com.baeldung.persistence.dao.common.AbstractHibernateDao;

@Repository
public class FooDao extends AbstractHibernateDao<Foo> implements IFooDao {

    public FooDao() {
        super();

        setClazz(Foo.class);
    }

    // API

}
