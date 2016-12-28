package baeldung.data;

import org.apache.deltaspike.data.api.EntityManagerResolver;

import javax.inject.Inject;
import javax.persistence.EntityManager;

public class SecondaryEntityManagerResolver implements EntityManagerResolver {

    @Inject
    @SecondaryPersistenceUnit
    private EntityManager entityManager;

    @Override
    public EntityManager resolveEntityManager() {
        return entityManager;
    }
}
