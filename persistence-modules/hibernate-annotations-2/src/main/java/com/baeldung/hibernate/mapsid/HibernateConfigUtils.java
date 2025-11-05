package com.baeldung.hibernate.mapsid;

import java.util.HashMap;
import java.util.Map;

import org.hibernate.SessionFactory;
import org.hibernate.boot.Metadata;
import org.hibernate.boot.MetadataSources;
import org.hibernate.boot.registry.StandardServiceRegistryBuilder;
import org.hibernate.cfg.Environment;
import org.hibernate.service.ServiceRegistry;

public class HibernateConfigUtils {

    public static SessionFactory sessionFactory() {
        ServiceRegistry serviceRegistry = new StandardServiceRegistryBuilder().applySettings(getProperties())
            .build();

        MetadataSources metadataSources = new MetadataSources(serviceRegistry);
        Metadata metadata = metadataSources.addAnnotatedClass(Person.class)
            .addAnnotatedClass(Address.class)
            .getMetadataBuilder()
            .build();

        return metadata.buildSessionFactory();
    }

    private static Map<String, Object> getProperties() {
        Map<String, Object> dbSettings = new HashMap<>();
        dbSettings.put(Environment.JAKARTA_JDBC_URL, "jdbc:h2:mem:mydbJoinColumn;DB_CLOSE_DELAY=-1");
        dbSettings.put(Environment.JAKARTA_JDBC_DRIVER, "org.h2.Driver");
        dbSettings.put(Environment.JAKARTA_JDBC_USER, "sa");
        dbSettings.put(Environment.JAKARTA_JDBC_PASSWORD, "");
        dbSettings.put(Environment.CURRENT_SESSION_CONTEXT_CLASS, "thread");
        dbSettings.put(Environment.SHOW_SQL, "true");
        dbSettings.put(Environment.HBM2DDL_AUTO, "create");

        return dbSettings;
    }

}
