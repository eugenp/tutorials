package com.baeldung.hibernate.fetching.util;

import org.hibernate.Session;
import org.hibernate.cfg.Configuration;

public class HibernateUtil {

<<<<<<< HEAD
	@SuppressWarnings("deprecation")
	public static Session getHibernateSession(String fetchMethod) {
		//two config files are there
		//one with lazy loading enabled 
		//another lazy = false
		SessionFactory sf = null;
		if ("lazy".equals(fetchMethod)) {
			sf = new Configuration().configure("fetchingLazy.cfg.xml").buildSessionFactory();
		} else {
			sf = new Configuration().configure("fetching.cfg.xml").buildSessionFactory();
		}
		
		// fetching.cfg.xml is used for this example
		final Session session = sf.openSession();
		return session;
	}

	public static Session getHibernateSession() {
		SessionFactory sf = null;
		sf = new Configuration().configure("fetching.cfg.xml").buildSessionFactory();
		final Session session = sf.openSession();
		return session;
	}
=======
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
>>>>>>> 91d12fe986fe93ce9bd17dff3c55d84a63d075c4

}
