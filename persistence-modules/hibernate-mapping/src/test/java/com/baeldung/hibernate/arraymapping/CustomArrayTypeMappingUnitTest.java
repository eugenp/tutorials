package com.baeldung.hibernate.arraymapping;

import static org.junit.Assert.fail;
import static org.junit.jupiter.api.Assertions.assertEquals;

import java.io.IOException;

import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;


public class CustomArrayTypeMappingUnitTest {

    private Session session;
    private Transaction transaction;

    @BeforeEach
    public void setup() throws IOException {
        try {
            session = HibernateSessionUtil.getSessionFactory().openSession();
            transaction = session.beginTransaction();

            bootstrapData();

        } catch (HibernateException | IOException e) {
            System.out.println("Can't connect to a PostgreSQL DB");
        }
    }

    @AfterEach
    public void cleanup() {
        if (null != session) { 
            transaction.rollback();
            session.close();
        }
    }

    @Test
    public void givenArrayMapping_whenQueried_thenReturnArraysFromDB() throws HibernateException, IOException {
        if (null != session) {
            User user = session.find(User.class, 1L);

            assertEquals("john", user.getName());
            assertEquals("superuser", user.getRoles()[0]);
            assertEquals("admin", user.getRoles()[1]);
            assertEquals(100, user.getLocations()[0]);
            assertEquals(389, user.getLocations()[1]);
            assertEquals("7000000000", user.getPhoneNumbers()[0]);
            assertEquals("8000000000", user.getPhoneNumbers()[1]);
        }
    }

    @Test
    public void givenArrayMapping_whenArraysAreInserted_thenPersistInDB() throws HibernateException, IOException {
        if (null != session) {
            transaction = session.beginTransaction();

            User user = new User();
            user.setId(2L);
            user.setName("smith");

            String[] roles = {"admin", "employee"};
            user.setRoles(roles);

            Integer[] locations = {190, 578};
            user.setLocations(locations);

            session.persist(user);
            session.flush();
            session.clear();

            transaction.commit();

            User userDBObj = session.find(User.class, 2L);

            assertEquals("smith", userDBObj.getName());
            assertEquals("admin", userDBObj.getRoles()[0]);
            assertEquals(578, userDBObj.getLocations()[1]);
        }
    }

    @Test
    public void givenArrayMapping_whenArrayIsUpdated_thenPersistInDB() throws HibernateException, IOException {
        if (null != session) {
            transaction = session.beginTransaction();

            User user = session.find(User.class, 1L);

            String[] updatedRoles = {"superuser", "superadmin"};
            String[] updatedPhoneNumbers = {"9000000000"};

            user.setRoles(updatedRoles);
            user.setPhoneNumbers(updatedPhoneNumbers);

            session.persist(user);
            session.flush();
            session.clear();

            User userDBObj = session.find(User.class, 1L);

            assertEquals("john", userDBObj.getName());
            assertEquals("superadmin", userDBObj.getRoles()[1]);
            assertEquals("9000000000", userDBObj.getPhoneNumbers()[0]);
        }
    }

    public void bootstrapData() {
        session.createQuery("delete from User").executeUpdate();

        User user = new User();
        user.setId(1L);
        user.setName("john");

        String[] roles = {"superuser", "admin"};
        user.setRoles(roles);

        Integer[] locations = {100, 389};
        user.setLocations(locations);

        String[] phoneNumbers = {"7000000000", "8000000000"};
        user.setPhoneNumbers(phoneNumbers);

        session.persist(user);
        session.flush();
        session.clear();

        transaction.commit();
    }

}
