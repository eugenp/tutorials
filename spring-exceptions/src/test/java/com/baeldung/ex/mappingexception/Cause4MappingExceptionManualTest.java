package com.baeldung.ex.mappingexception;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

import com.baeldung.ex.mappingexception.cause4.persistence.model.Foo;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.boot.registry.StandardServiceRegistryBuilder;
import org.hibernate.cfg.Configuration;
import org.hibernate.service.ServiceRegistry;
import org.junit.Test;

public class Cause4MappingExceptionManualTest {

    // tests

    @Test
    public final void givenEntityIsPersisted_thenException() throws IOException {
        final SessionFactory sessionFactory = configureSessionFactory();

        final Session session = sessionFactory.openSession();
        session.beginTransaction();
        session.saveOrUpdate(new Foo());
        session.getTransaction().commit();
    }

    private final SessionFactory configureSessionFactory() throws IOException {
        final Configuration configuration = new Configuration();
        final InputStream inputStream = this.getClass().getClassLoader().getResourceAsStream("hibernate-mysql.properties");
        final Properties hibernateProperties = new Properties();
        hibernateProperties.load(inputStream);
        configuration.setProperties(hibernateProperties);

        configuration.addAnnotatedClass(Foo.class);

        final ServiceRegistry serviceRegistry = new StandardServiceRegistryBuilder().applySettings(configuration.getProperties()).build();
        final SessionFactory sessionFactory = configuration.buildSessionFactory(serviceRegistry);
        return sessionFactory;
    }

}
