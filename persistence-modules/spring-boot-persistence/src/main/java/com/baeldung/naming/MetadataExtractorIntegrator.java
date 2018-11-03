package com.baeldung.naming;

import org.hibernate.boot.Metadata;
import org.hibernate.boot.model.relational.Database;
import org.hibernate.engine.spi.SessionFactoryImplementor;
import org.hibernate.integrator.spi.Integrator;
import org.hibernate.service.spi.SessionFactoryServiceRegistry;

/**
 * Custom implementation of the {@link Integrator Integrator} interface.
 * We can use it to getting access to the binding metadata between entity mappings and database tables.
 */
public class MetadataExtractorIntegrator implements Integrator {

    public static final MetadataExtractorIntegrator INSTANCE = new MetadataExtractorIntegrator();

    private Database database;

    private Metadata metadata;

    public Database getDatabase() {
        return database;
    }

    public Metadata getMetadata() {
        return metadata;
    }

    @Override
    public void integrate(Metadata metadata, SessionFactoryImplementor sessionFactory, SessionFactoryServiceRegistry serviceRegistry) {

        this.database = metadata.getDatabase();
        this.metadata = metadata;

    }

    @Override
    public void disintegrate(SessionFactoryImplementor sessionFactory, SessionFactoryServiceRegistry serviceRegistry) {

    }
}
