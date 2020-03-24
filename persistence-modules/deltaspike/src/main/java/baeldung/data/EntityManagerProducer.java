package baeldung.data;

import javax.enterprise.context.RequestScoped;
import javax.enterprise.inject.Produces;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

public class EntityManagerProducer {

    @PersistenceContext(unitName = "primary") private EntityManager entityManager;

    @RequestScoped
    @Produces
    public EntityManager create() {
        return entityManager;
    }

}