package com.baeldung.hexagonal.jpa;

import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import org.glassfish.hk2.api.Factory;

/**
 * HK2 {@link Factory} for providing the {@link EntityManagerFactory} singleton.
 */
final class EMFFactory implements Factory<EntityManagerFactory> {

    @Override
    public EntityManagerFactory provide() {
        return Persistence.createEntityManagerFactory("com.baeldung.hexagonal");
    }

    @Override
    public void dispose(EntityManagerFactory entityManagerFactory) {
        entityManagerFactory.close();
    }
}
