package com.baeldung.hibernate.bootstrap;

import com.baeldung.hibernate.pojo.Movie;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.boot.Metadata;
import org.hibernate.boot.MetadataSources;
import org.hibernate.boot.registry.BootstrapServiceRegistry;
import org.hibernate.boot.registry.BootstrapServiceRegistryBuilder;
import org.hibernate.boot.registry.StandardServiceRegistryBuilder;
import org.hibernate.service.ServiceRegistry;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import java.io.IOException;
import java.util.List;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

public class Hibernate5BootstrapAPITest {

    SessionFactory sessionFactory = null;
    Session session = null;

    @Before
    public void setUp() throws IOException {

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
    }

    @Test
    public void testBuildSessionFactory() {
        assertNotNull(sessionFactory);
        session = sessionFactory.openSession();
        assertNotNull(session);

        //Persist Movie
        session.getTransaction().begin();
        Movie movie = new Movie();
        movie.setId(100L);
        session.persist(movie);
        session.getTransaction().commit();

        List<Movie> movies = session.createQuery("FROM Movie").list();
        assertNotNull(movies);
        assertEquals(movies.size(), 1L);
    }

    @After
    public void clean() throws IOException {
        session.close();
        sessionFactory.close();
    }
}
