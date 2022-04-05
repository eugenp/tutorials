package baeldung.data;

import javax.enterprise.context.ApplicationScoped;
import javax.enterprise.inject.Produces;
import javax.enterprise.inject.Specializes;
import javax.persistence.EntityManager;
import javax.persistence.Persistence;

/**
 * Created by adam.
 */
public class TestEntityManagerProducer extends EntityManagerProducer {

    @ApplicationScoped
    @Produces
    @Specializes
    public EntityManager create() {
        return Persistence
          .createEntityManagerFactory("pu-test")
          .createEntityManager();
    }

}
