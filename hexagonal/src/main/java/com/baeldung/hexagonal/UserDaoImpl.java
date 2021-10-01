package com.baeldung.hexagonal;

import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

@Repository
public class UserDaoImpl implements UserDaoInterface {
    @PersistenceContext
    private EntityManager entityManager;

    @Override
    public User find(String username) {
        return entityManager.find(User.class,username);
    }
}
