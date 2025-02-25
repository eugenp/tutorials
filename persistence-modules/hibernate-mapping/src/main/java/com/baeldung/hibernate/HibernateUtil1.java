package com.baeldung.hibernate;

import org.hibernate.SessionFactory;
import org.hibernate.boot.registry.StandardServiceRegistryBuilder;
import org.hibernate.cfg.Configuration;
import org.hibernate.service.ServiceRegistry;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.baeldung.hibernate.uuids.Element;
import com.baeldung.hibernate.uuids.Reservation;
import com.baeldung.hibernate.uuids.Sale;
import com.baeldung.hibernate.uuids.WebSiteUser;

public class HibernateUtil1 {


    private static final String DEFAULT_RESOURCE = "manytomany.cfg.xml";
    private static final Logger LOGGER = LoggerFactory.getLogger(HibernateUtil.class);

    private static SessionFactory buildSessionFactory(String resource) {
        try {
            // Create the SessionFactory from hibernate-annotation.cfg.xml
            Configuration configuration = new Configuration();
            configuration.addAnnotatedClass(WebSiteUser.class);
            configuration.addAnnotatedClass(Element.class);
            configuration.addAnnotatedClass(Reservation.class);
            configuration.addAnnotatedClass(Sale.class);
            configuration.configure(resource);
            LOGGER.debug("Hibernate Annotation Configuration loaded");

            ServiceRegistry serviceRegistry = new StandardServiceRegistryBuilder().applySettings(configuration.getProperties())
                .build();
            LOGGER.debug("Hibernate Annotation serviceRegistry created");

            return configuration.buildSessionFactory(serviceRegistry);
        } catch (Throwable ex) {
            LOGGER.error("Initial SessionFactory creation failed.", ex);
            throw new ExceptionInInitializerError(ex);
        }
    }

    public static SessionFactory getSessionFactory() {
        return buildSessionFactory(DEFAULT_RESOURCE);
    }

    public static SessionFactory getSessionFactory(String resource) {
        return buildSessionFactory(resource);
    }

}
