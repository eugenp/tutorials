package com.baeldung.hexagonal.jpa;

import com.baeldung.hexagonal.persistence.PersonFactory;
import com.baeldung.hexagonal.persistence.PersonRepository;
import javax.inject.Singleton;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import org.glassfish.hk2.utilities.binding.AbstractBinder;
import org.glassfish.jersey.process.internal.RequestScoped;

/**
 * HK2 binder which allows developers to swap in the "JPA" implementation of the persistence
 * abstraction.
 */
public class JPABinder extends AbstractBinder {

    @Override
    protected void configure() {
        bindFactory(EMFFactory.class).to(EntityManagerFactory.class)
            .in(Singleton.class);
        bindFactory(EMFactory.class).to(EntityManager.class)
            .in(RequestScoped.class);
        bind(PersonEntityFactory.class).to(PersonFactory.class);
        bind(PersonEntityRepository.class).to(PersonRepository.class);
    }
}
