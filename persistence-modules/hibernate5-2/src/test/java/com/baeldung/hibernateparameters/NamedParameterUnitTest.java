package com.baeldung.hibernateparameters;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.boot.MetadataSources;
import org.hibernate.boot.registry.StandardServiceRegistry;
import org.hibernate.boot.registry.StandardServiceRegistryBuilder;
import org.hibernate.query.Query;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.assertEquals;
import static org.junit.jupiter.api.Assertions.fail;

public class NamedParameterUnitTest {
    private SessionFactory sessionFactory;

    @Before
    public void setUp() throws Exception {
        final StandardServiceRegistry registry = new StandardServiceRegistryBuilder()
                .configure()
                .build();
        try {
            sessionFactory = new MetadataSources(registry).buildMetadata().buildSessionFactory();
            Session session = sessionFactory.openSession();
            session.beginTransaction();
            session.save(new Event("Event 1"));
            session.save(new Event("Event 2"));
            session.getTransaction().commit();
            session.close();
        } catch (Exception e) {
            fail(e);
            StandardServiceRegistryBuilder.destroy(registry);
        }
    }

    @After
    public void tearDown() throws Exception {
        if (sessionFactory != null) {
            sessionFactory.close();
        }
    }

    @Test
    public void whenNamedParameterProvided_thenCorrect() {
        Session session = sessionFactory.openSession();
        session.beginTransaction();
        Query<Event> query = session.createQuery("from Event E WHERE E.title = :eventTitle", Event.class);

        // This binds the value "Event1" to the parameter :eventTitle
        query.setParameter("eventTitle", "Event 1");

        assertEquals(1, query.list().size());
        session.getTransaction().commit();
        session.close();
    }

    @Test(expected = org.hibernate.QueryException.class)
    public void whenNamedParameterMissing_thenThrowsQueryException() {
        Session session = sessionFactory.openSession();
        session.beginTransaction();
        Query<Event> query = session.createQuery("from Event E WHERE E.title = :eventTitle", Event.class);

        try {
            query.list();
            fail("We are expecting an exception!");
        } finally {
            session.getTransaction().commit();
            session.close();
        }
    }
}
