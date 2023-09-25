package com.baeldung.hibernate.bootstrap;

import com.baeldung.hibernate.bootstrap.model.TestEntity;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.transaction.annotation.Transactional;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = { HibernateXMLConf.class })
@Transactional
public class HibernateXMLBootstrapIntegrationTest {

    @Autowired
    private SessionFactory sessionFactory;

    @Test
    public void whenBootstrapHibernateSession_thenNoException() {

        Session session = sessionFactory.getCurrentSession();

        TestEntity newEntity = new TestEntity();
        newEntity.setId(1);
        session.persist(newEntity);

        TestEntity searchEntity = session.find(TestEntity.class, 1);

        Assert.assertNotNull(searchEntity);
    }

}
