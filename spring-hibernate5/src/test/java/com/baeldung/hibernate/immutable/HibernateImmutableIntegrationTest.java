package com.baeldung.hibernate.immutable;

import com.baeldung.hibernate.immutable.entities.Event;
import com.baeldung.hibernate.immutable.util.HibernateUtil;
import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.junit.After;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

public class HibernateImmutableIntegrationTest {

    private Session session;

    @Rule
    public final ExpectedException exception = ExpectedException.none();

    @Before
    public void setup() {
        session = HibernateUtil.getSessionFactory().getCurrentSession();
        session.beginTransaction();
        createEvent();
    }

    @After
    public void teardown() {
        HibernateUtil.getSessionFactory().close();
    }

    @Test
    public void addEvent() {
        Event event = new Event();
        event.setTitle("Public Event");
        session.save(event);
        session.getTransaction().commit();
    }

    @Test
    public void updateEvent() {
        Event event = (Event) session.createQuery(
                "FROM Event WHERE title='My Event'").list().get(0);
        event.setTitle("Private Event");
        session.saveOrUpdate(event);
        session.getTransaction().commit();
    }

    @Test
    public void deleteEvent() {
        Event event = (Event) session.createQuery(
                "FROM Event WHERE title='My Event'").list().get(0);
        session.delete(event);
        session.getTransaction().commit();
    }

    @Test
    public void addGuest() {
        Event event = (Event) session.createQuery(
                "FROM Event WHERE title='New Event'").list().get(0);
        String newGuest = "Sara";
        event.getGuestList().add(newGuest);

        exception.expect(HibernateException.class);
        session.save(event);
        session.getTransaction().commit();
    }

    @Test
    public void deleteCascade() {
        Event event = (Event) session.createQuery(
                "FROM Event WHERE title='New Event'").list().get(0);
        String guest = event.getGuestList().iterator().next();
        event.getGuestList().remove(guest);

        exception.expect(HibernateException.class);
        session.saveOrUpdate(event);
        session.getTransaction().commit();
    }

    public void createEvent() {
        Event event = new Event();
        event.setTitle("New Event");
        session.save(event);
    }
}
