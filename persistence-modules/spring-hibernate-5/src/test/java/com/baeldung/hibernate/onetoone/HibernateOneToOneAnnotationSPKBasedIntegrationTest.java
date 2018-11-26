package com.baeldung.hibernate.onetoone;

import com.baeldung.hibernate.onetoone.sharedkeybased.Address;
import com.baeldung.hibernate.onetoone.sharedkeybased.User;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

import java.util.List;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

public class HibernateOneToOneAnnotationSPKBasedIntegrationTest {

    private static SessionFactory sessionFactory;

    private Session session;

    @BeforeClass
    public static void beforeTests() {
        sessionFactory = HibernateUtil.getSessionFactory(Strategy.SHARED_PRIMARY_KEY);
    }

    @Before
    public void setUp() {
        session = sessionFactory.openSession();
        session.beginTransaction();
    }

    @Test
    public void givenData_whenInsert_thenCreates1to1relationship() {
        User user = new User();
        user.setUserName("alice@baeldung.com");

        Address address = new Address();
        address.setStreet("SPK Street");
        address.setCity("SPK City");

        address.setUser(user);
        user.setAddress(address);

        //Address entry will automatically be created by hibernate, since cascade type is specified as ALL
        session.persist(user);
    }

    @After
    public void tearDown() {
        session.getTransaction()
                .commit();
        session.close();
    }

    @Test
    public void givenSession_whenRead_thenReturns1to1data() {
        @SuppressWarnings("unchecked")
        List<User> userList = session.createQuery("FROM User").list();
        assertNotNull(userList);
        for (User user : userList) {
            assertEquals("alice@baeldung.com", user.getUserName());
            assertNotNull(user.getAddress());
            assertEquals("SPK Street", user.getAddress().getStreet());
            assertEquals("SPK City", user.getAddress().getCity());

        }
    }

    @AfterClass
    public static void afterTests() {
        sessionFactory.close();
    }
}
