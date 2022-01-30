package com.baeldung.hibernate.immutable.util;

import com.baeldung.hibernate.immutable.entities.Event;
import com.baeldung.hibernate.immutable.entities.EventGeneratedId;
import org.hibernate.SessionFactory;
import org.hibernate.boot.registry.StandardServiceRegistryBuilder;
import org.hibernate.cfg.Configuration;
import org.hibernate.service.ServiceRegistry;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class HibernateUtil {

    private static final Logger LOGGER = LoggerFactory.getLogger(HibernateUtil.class);
    private static final SessionFactory sessionFactory = buildSessionFactory();

    private static SessionFactory buildSessionFactory() {
        try {
            // Create a session factory from immutable.cfg.xml
            Configuration configuration = new Configuration();
            configuration.addAnnotatedClass(Event.class);
            configuration.addAnnotatedClass(EventGeneratedId.class);
            configuration.configure("immutable.cfg.xml");
            ServiceRegistry serviceRegistry = new StandardServiceRegistryBuilder().applySettings(configuration.getProperties()).build();
            return configuration.buildSessionFactory(serviceRegistry);
        } catch (Throwable ex) {
            LOGGER.debug("Initial SessionFactory creation failed.", ex);
            throw new ExceptionInInitializerError(ex);
        }
    }

    public static SessionFactory getSessionFactory() {
        return sessionFactory;
    }
}
