package com.baeldung.hibernate.oneToMany.config;

import com.baeldung.hibernate.oneToMany.model.Cart;
import com.baeldung.hibernate.oneToMany.model.CartOIO;
import com.baeldung.hibernate.oneToMany.model.Item;
import com.baeldung.hibernate.oneToMany.model.ItemOIO;
import org.hibernate.SessionFactory;
import org.hibernate.boot.Metadata;
import org.hibernate.boot.MetadataSources;
import org.hibernate.boot.registry.StandardServiceRegistryBuilder;
import org.hibernate.cfg.Environment;
import org.hibernate.service.ServiceRegistry;

import java.util.HashMap;
import java.util.Map;

public class HibernateAnnotationUtil {

    private static final SessionFactory SESSION_FACTORY = buildSessionFactory();

    /**
     * Utility class
     */
    private HibernateAnnotationUtil() {
    }

    public static SessionFactory getSessionFactory() {
        return SESSION_FACTORY;
    }

    private static SessionFactory buildSessionFactory() {
        ServiceRegistry serviceRegistry = new StandardServiceRegistryBuilder()
          .applySettings(dbSettings())
          .build();

        Metadata metadata = new MetadataSources(serviceRegistry)
          .addAnnotatedClass(Cart.class)
          .addAnnotatedClass(CartOIO.class)
          .addAnnotatedClass(Item.class)
          .addAnnotatedClass(ItemOIO.class)
          .buildMetadata();

        return metadata.buildSessionFactory();
    }

    private static Map<String, String> dbSettings() {
        Map<String, String> dbSettings = new HashMap<>();
        dbSettings.put(Environment.URL, "jdbc:h2:mem:spring_hibernate_one_to_many");
        dbSettings.put(Environment.USER, "sa");
        dbSettings.put(Environment.PASS, "");
        dbSettings.put(Environment.DRIVER, "org.h2.Driver");
        dbSettings.put(Environment.CURRENT_SESSION_CONTEXT_CLASS, "thread");
        dbSettings.put(Environment.SHOW_SQL, "true");
        dbSettings.put(Environment.HBM2DDL_AUTO, "create");
        return dbSettings;
    }
}
