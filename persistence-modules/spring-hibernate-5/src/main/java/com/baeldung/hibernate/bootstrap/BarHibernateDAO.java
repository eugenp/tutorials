package com.baeldung.hibernate.bootstrap;

import com.baeldung.hibernate.bootstrap.model.TestEntity;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;

public abstract class BarHibernateDAO {

    @Autowired
    private SessionFactory sessionFactory;

    public TestEntity findEntity(int id) {

        return getCurrentSession().find(TestEntity.class, 1);
    }

    public void createEntity(TestEntity entity) {

        getCurrentSession().save(entity);
    }

    public void createEntity(int id, String newDescription) {

        TestEntity entity = findEntity(id);
        entity.setDescription(newDescription);
        getCurrentSession().save(entity);
    }

    public void deleteEntity(int id) {

        TestEntity entity = findEntity(id);
        getCurrentSession().delete(entity);
    }

    protected Session getCurrentSession() {
        return sessionFactory.getCurrentSession();
    }
}
