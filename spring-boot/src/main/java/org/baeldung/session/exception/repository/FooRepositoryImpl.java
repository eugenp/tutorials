package org.baeldung.session.exception.repository;

import org.baeldung.demo.model.Foo;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Repository;

@Profile("exception")
@Repository
public class FooRepositoryImpl implements FooRepository {
    @Autowired
    private SessionFactory sessionFactory;

    @Override
    public void save(Foo foo) {
        sessionFactory.getCurrentSession()
            .saveOrUpdate(foo);
    }

    @Override
    public Foo get(Integer id) {
        return sessionFactory.getCurrentSession()
            .get(Foo.class, id);
    }

}