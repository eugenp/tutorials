package com.baeldung.hibernate.criteria.util;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;

public class HibernateUtil {

    public static Session getHibernateSession() {

        final SessionFactory sf = new Configuration().configure("criteria.cfg.xml").buildSessionFactory();

        final Session session = sf.openSession();
        return session;
    }

}
