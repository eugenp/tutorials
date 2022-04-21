package com.baeldung.hibernate.exception.persistentobject;

import java.util.Properties;

import org.hibernate.SessionFactory;
import org.hibernate.boot.registry.StandardServiceRegistryBuilder;
import org.hibernate.cfg.Configuration;
import org.hibernate.cfg.Environment;
import org.hibernate.service.ServiceRegistry;

import com.baeldung.hibernate.exception.persistentobject.entity.Article;
import com.baeldung.hibernate.exception.persistentobject.entity.Author;
import com.baeldung.hibernate.exception.persistentobject.entity.Book;

public class HibernateUtil {
    private static SessionFactory sessionFactory;

    public static SessionFactory getSessionFactory() {
        if (sessionFactory == null) {
            try {
                Configuration configuration = new Configuration();
                Properties settings = new Properties();

                settings.put(Environment.DRIVER, "org.hsqldb.jdbcDriver");
                settings.put(Environment.URL, "jdbc:hsqldb:mem:userrole");
                settings.put(Environment.USER, "sa");
                settings.put(Environment.PASS, "");
                settings.put(Environment.DIALECT, "org.hibernate.dialect.HSQLDialect");
                settings.put(Environment.SHOW_SQL, "true");
                settings.put(Environment.HBM2DDL_AUTO, "update");
                settings.put(Environment.CHECK_NULLABILITY, "true");

                configuration.setProperties(settings);
                configuration.addAnnotatedClass(Book.class);
                configuration.addAnnotatedClass(Author.class);
                configuration.addAnnotatedClass(Article.class);

                ServiceRegistry serviceRegistry = new StandardServiceRegistryBuilder()
                        .applySettings(configuration.getProperties()).build();
                sessionFactory = configuration.buildSessionFactory(serviceRegistry);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        return sessionFactory;
    }
}