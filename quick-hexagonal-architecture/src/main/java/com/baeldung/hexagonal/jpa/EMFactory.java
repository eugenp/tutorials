package com.baeldung.hexagonal.jpa;

import javax.inject.Inject;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import org.glassfish.hk2.api.Factory;
import org.glassfish.jersey.server.CloseableService;

/**
 * HK2 Factory for providing request scoped {@link javax.persistence.EntityManager} instnaces. Automatically closes the entity manager when the request concludes.
 */
class EMFactory implements Factory<EntityManager> {

    private final EntityManagerFactory emf;
    private final CloseableService closeableService;

    @Inject
    EMFactory(EntityManagerFactory emf, CloseableService closeableService) {
        this.emf = emf;
        this.closeableService = closeableService;
    }

    @Override
    public EntityManager provide() {
        final EntityManager em = emf.createEntityManager();
        closeableService.add(em::close);
        return em;
    }

    @Override
    public void dispose(EntityManager em) {
        if (em.isOpen()) {
            em.close();
        }
    }
}
