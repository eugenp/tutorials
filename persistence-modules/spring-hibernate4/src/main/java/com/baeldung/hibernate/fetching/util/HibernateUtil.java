package com.baeldung.hibernate.fetching.util;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;

public class HibernateUtil {

    @SuppressWarnings("deprecation")
    public static Session getHibernateSession(String fetchMethod) {
        // two config files are there
        // one with lazy loading enabled
        // another lazy = false
        SessionFactory sf;
        if ("lazy".equals(fetchMethod)) {
            sf = new Configuration().configure("fetchingLazy.cfg.xml").buildSessionFactory();
        } else {
            sf = new Configuration().configure("fetching.cfg.xml").buildSessionFactory();
        }

        // fetching.cfg.xml is used for this example
        return sf.openSession();
    }

    public static Session getHibernateSession() {
        return new Configuration().configure("fetching.cfg.xml").buildSessionFactory().openSession();
    }

}
