package com.baeldung.hibernate.bootstrap;

import com.baeldung.hibernate.pojo.Movie;
import org.hibernate.SessionFactory;
import org.hibernate.boot.Metadata;
import org.hibernate.boot.MetadataSources;
import org.hibernate.boot.registry.BootstrapServiceRegistry;
import org.hibernate.boot.registry.BootstrapServiceRegistryBuilder;
import org.hibernate.boot.registry.StandardServiceRegistryBuilder;
import org.hibernate.service.ServiceRegistry;
import org.junit.After;
import org.junit.Test;

import java.io.IOException;

import static org.junit.Assert.assertNotNull;

public class BootstrapAPIIntegrationTest {

    SessionFactory sessionFactory = null;

    @Test
    public void whenServiceRegistryAndMetadata_thenSessionFactory() throws IOException {

        BootstrapServiceRegistry bootstrapRegistry = new BootstrapServiceRegistryBuilder()
                .build();

        ServiceRegistry standardRegistry = new StandardServiceRegistryBuilder(bootstrapRegistry)
                // No need for hibernate.cfg.xml file, an hibernate.properties is sufficient.
                //.configure()
                .build();

        MetadataSources metadataSources = new MetadataSources(standardRegistry);
        metadataSources.addAnnotatedClass(Movie.class);

        Metadata metadata = metadataSources.getMetadataBuilder().build();

        sessionFactory = metadata.buildSessionFactory();
        assertNotNull(sessionFactory);
        sessionFactory.close();
    }

    @After
    public void clean() throws IOException {
        sessionFactory.close();
    }
}
