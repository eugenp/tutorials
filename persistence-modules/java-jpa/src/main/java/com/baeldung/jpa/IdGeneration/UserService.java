package com.baeldung.jpa.IdGeneration;

import jakarta.persistence.EntityManager;
import jakarta.transaction.Transactional;

public class UserService {

    EntityManager entityManager;

    public UserService(EntityManager entityManager) {
        this.entityManager = entityManager;
    }

    @Transactional
    public long saveUser(User user){
        entityManager.persist(user);
        return user.getId();
    }
}
