package com.baeldung.jpa.IdGeneration;

import javax.persistence.EntityManager;
import javax.transaction.Transactional;

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
