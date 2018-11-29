package com.baeldung.hibernate.onetoone;

import org.hibernate.SessionFactory;
import org.hibernate.boot.registry.StandardServiceRegistryBuilder;
import org.hibernate.cfg.Configuration;
import org.hibernate.service.ServiceRegistry;

public class HibernateUtil {
    private static SessionFactory sessionFactory;

    private static SessionFactory buildSessionFactory(Strategy strategy) {
        try {
            // Create the SessionFactory from hibernate-annotation.cfg.xml
            Configuration configuration = new Configuration();

            for (Class<?> entityClass : strategy.getEntityClasses()) {
                configuration.addAnnotatedClass(entityClass);
            }
            configuration.configure("onetoone.cfg.xml");

            ServiceRegistry serviceRegistry = new StandardServiceRegistryBuilder().applySettings(configuration.getProperties())
                    .build();

            SessionFactory sessionFactory = configuration.buildSessionFactory(serviceRegistry);

            return sessionFactory;
        } catch (Throwable ex) {
            ex.printStackTrace();
            throw new ExceptionInInitializerError(ex);
        }
    }

    public static SessionFactory getSessionFactory(Strategy strategy) {
        if (sessionFactory == null)
            sessionFactory = buildSessionFactory(strategy);
        return sessionFactory;
    }
}
