package com.baeldung.hibernate.criteria.util;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;

public class HibernateUtil {
	private static SessionFactory factory;

	@SuppressWarnings("deprecation")
	public static Session getHibernateSession() {

		factory = new Configuration().configure().buildSessionFactory();
		final Session session = factory.openSession();
		return session;

	}

}
