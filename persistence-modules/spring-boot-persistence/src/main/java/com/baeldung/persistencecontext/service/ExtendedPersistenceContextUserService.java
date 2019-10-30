package com.baeldung.persistencecontext.service;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.PersistenceContextType;
import javax.transaction.Transactional;

import org.springframework.stereotype.Component;

import com.baeldung.persistencecontext.entity.User;

@Component
public class ExtendedPersistenceContextUserService {

    @PersistenceContext(type = PersistenceContextType.EXTENDED)
    private EntityManager entityManager;

    public EntityManager getEntityManager() {
        return entityManager;
    }
    
    @Transactional
    public User insertWithTransaction(User user) {
        entityManager.persist(user);
        return user;
    }

    public User insertWithoutTransaction(User user) {
        entityManager.persist(user);
        return user;
    }

    public User find(long id) {
        User user = entityManager.find(User.class, id);
        return user;
    }
}
