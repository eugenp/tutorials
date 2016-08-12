package com.baeldung.hibernate.fetching.util;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;

public class HibernateUtil {
	private static SessionFactory factory;

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

}
