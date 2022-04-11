package com.baeldung.hibernate.oneToMany.config;

import com.baeldung.hibernate.oneToMany.model.Cart;
import com.baeldung.hibernate.oneToMany.model.CartOIO;
import com.baeldung.hibernate.oneToMany.model.Item;
import com.baeldung.hibernate.oneToMany.model.ItemOIO;
import org.hibernate.SessionFactory;
import org.hibernate.boot.Metadata;
import org.hibernate.boot.MetadataSources;
import org.hibernate.boot.model.naming.ImplicitNamingStrategyJpaCompliantImpl;
import org.hibernate.boot.registry.StandardServiceRegistryBuilder;
import org.hibernate.service.ServiceRegistry;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class HibernateAnnotationUtil {

    private static final Logger LOGGER = LoggerFactory.getLogger(HibernateAnnotationUtil.class);

    private static SessionFactory sessionFactory;

    public static SessionFactory getSessionFactory() {
        if (sessionFactory == null) {
            sessionFactory = buildSessionFactory();
        }
        return sessionFactory;
    }

    private static SessionFactory buildSessionFactory() {
        try {
            ServiceRegistry serviceRegistry = new StandardServiceRegistryBuilder()
              .configure("hibernate-annotation.cfg.xml")
              .build();

            Metadata metadata = new MetadataSources(serviceRegistry)
              .addAnnotatedClass(Cart.class)
              .addAnnotatedClass(CartOIO.class)
              .addAnnotatedClass(Item.class)
              .addAnnotatedClass(ItemOIO.class)
              .getMetadataBuilder()
              .applyImplicitNamingStrategy(ImplicitNamingStrategyJpaCompliantImpl.INSTANCE)
              .build();

            return metadata.getSessionFactoryBuilder().build();

        } catch (Throwable ex) {
            LOGGER.error("Initial SessionFactory creation failed.", ex);
            throw new ExceptionInInitializerError(ex);
        }
    }
}
