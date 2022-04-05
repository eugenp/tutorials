package com.baeldung.hibernate.criteria.util;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;

public class HibernateUtil {

    @SuppressWarnings("deprecation")
    public static Session getHibernateSession() {

        final SessionFactory sf = new Configuration().configure("criteria.cfg.xml").buildSessionFactory();

        // factory = new Configuration().configure().buildSessionFactory();
        final Session session = sf.openSession();
        return session;
    }

}
