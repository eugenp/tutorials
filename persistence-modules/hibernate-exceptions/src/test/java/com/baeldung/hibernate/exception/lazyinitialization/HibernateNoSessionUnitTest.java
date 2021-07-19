package com.baeldung.hibernate.exception.lazyinitialization;

import org.hibernate.LazyInitializationException;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.junit.AfterClass;
import org.junit.Assert;
import org.junit.BeforeClass;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

import com.baeldung.hibernate.exception.lazyinitialization.entity.Role;
import com.baeldung.hibernate.exception.lazyinitialization.entity.User;

public class HibernateNoSessionUnitTest {

    private static SessionFactory sessionFactory;

    @Rule
    public ExpectedException thrown = ExpectedException.none();

    @BeforeClass
    public static void init() {
        sessionFactory = HibernateUtil.getSessionFactory();
    }

    @Test
    public void whenAccessUserRolesInsideSession_thenSuccess() {

        User detachedUser = createUserWithRoles();

        Session session = sessionFactory.openSession();
        session.beginTransaction();

        User persistentUser = session.find(User.class, detachedUser.getId());

        Assert.assertEquals(2, persistentUser.getRoles().size());

        session.getTransaction().commit();
        session.close();
    }

    @Test
    public void whenAccessUserRolesOutsideSession_thenThrownException() {

        User detachedUser = createUserWithRoles();

        Session session = sessionFactory.openSession();
        session.beginTransaction();

        User persistentUser = session.find(User.class, detachedUser.getId());

        session.getTransaction().commit();
        session.close();

        thrown.expect(LazyInitializationException.class);
        System.out.println(persistentUser.getRoles().size());
    }

    private User createUserWithRoles() {

        Role admin = new Role("Admin");
        Role dba = new Role("DBA");

        User user = new User("Bob", "Smith");

        user.addRole(admin);
        user.addRole(dba);

        Session session = sessionFactory.openSession();
        session.beginTransaction();
        user.getRoles().forEach(role -> session.save(role));
        session.save(user);
        session.getTransaction().commit();
        session.close();

        return user;
    }

    @AfterClass
    public static void cleanUp() {
        sessionFactory.close();
    }
}
