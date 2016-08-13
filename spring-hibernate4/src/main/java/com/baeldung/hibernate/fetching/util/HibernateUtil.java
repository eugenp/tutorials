package com.baeldung.hibernate.fetching.util;

import org.hibernate.Session;
import org.hibernate.cfg.Configuration;

public class HibernateUtil {

    @SuppressWarnings("deprecation")
    public static Session getHibernateSession(String fetchMethod) {
        //two config files are there
        //one with lazy loading enabled
        //another lazy = false

        final String configFileName = "lazy".equals(fetchMethod) ?
          "fetchingLazy.cfg.xml" :
          "fetching.cfg.xml";

        return new Configuration()
          .configure(configFileName)
          .buildSessionFactory().openSession();
    }

    public static Session getHibernateSession() {
        return new Configuration()
          .configure("fetching.cfg.xml")
          .buildSessionFactory()
          .openSession();
    }

}
