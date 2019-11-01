package com.baeldung.hibernate.interceptors;

import java.io.IOException;
import java.io.Serializable;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.Test;

import com.baeldung.hibernate.interceptors.entity.User;

public class HibernateInterceptorUnitTest {
    private static SessionFactory sessionFactory;
    private static Serializable userId;

    @Before
    public void init() throws IOException {
        sessionFactory = HibernateUtil.getSessionFactoryWithInterceptor(null, new CustomInterceptor());
    }
    
    @AfterClass
    public static void finish() {
        if(userId != null) {
            Session session = sessionFactory.getCurrentSession();
            Transaction transaction = session.beginTransaction();
            User user = session.load(User.class, userId);
            if(user != null) {
                session.delete(user);
            }
            transaction.commit();
            session.close();
        }
    }
    
    @Test
    public void givenHibernateInterceptorAndSessionScoped_whenUserCreated_shouldSucceed() {
        Session session = sessionFactory.withOptions().interceptor(new CustomInterceptor()).openSession();
        User user = new User("Benjamin Franklin");
        Transaction transaction = session.beginTransaction();
        userId = session.save(user);
        transaction.commit();
        session.close();
    }
    
    @Test
    public void givenHibernateInterceptorAndSessionFactoryScoped_whenUserModified_shouldSucceed() {
        Session session = sessionFactory.openSession();
        Transaction transaction = session.beginTransaction();
        User user = session.load(User.class, userId);
        if(user != null) {
            user.setAbout("I am a scientist.");
            session.update(user);
        }
        transaction.commit();
        session.close();
    }
}
