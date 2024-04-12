package com.baeldung.hibernate.exception.detachedentity;

import java.util.Properties;

import org.hibernate.SessionFactory;
import org.hibernate.boot.registry.StandardServiceRegistryBuilder;
import org.hibernate.cfg.Configuration;
import org.hibernate.cfg.Environment;
import org.hibernate.service.ServiceRegistry;

import com.baeldung.hibernate.exception.detachedentity.entity.Comment;
import com.baeldung.hibernate.exception.detachedentity.entity.Post;

public class HibernateUtil {
    private static SessionFactory sessionFactory;

    public static SessionFactory getSessionFactory() {
        if (sessionFactory == null) {
            try {
                Configuration configuration = new Configuration();
                Properties settings = new Properties();
                settings.put(Environment.DRIVER, "org.hsqldb.jdbcDriver");
                settings.put(Environment.URL, "jdbc:hsqldb:mem:transient");
                settings.put(Environment.USER, "sa");
                settings.put(Environment.PASS, "");
                settings.put(Environment.DIALECT, "org.hibernate.dialect.HSQLDialect");
                // enable to show Hibernate generated SQL
                settings.put(Environment.SHOW_SQL, "false");
                settings.put(Environment.FORMAT_SQL, "true");
                settings.put(Environment.USE_SQL_COMMENTS, "true");
                settings.put(Environment.HBM2DDL_AUTO, "update");
                configuration.setProperties(settings);

                configuration.addAnnotatedClass(Comment.class);
                configuration.addAnnotatedClass(Post.class);

                ServiceRegistry serviceRegistry = new StandardServiceRegistryBuilder().applySettings(configuration.getProperties())
                    .build();
                sessionFactory = configuration.buildSessionFactory(serviceRegistry);

            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        return sessionFactory;
    }
}