package com.baeldung.hexagonalarchitecture.repository;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import org.springframework.stereotype.Repository;

import com.baeldung.hexagonalarchitecture.model.User;

@Repository
public class UserRepositoryAdapter implements UserRepositoryPort {

    @PersistenceContext
    private EntityManager entityManager;

    @Override
    public void create(User user) {
        entityManager.persist(user);
    }

    @Override
    public User getUser(Long id) {
        return entityManager.find(User.class, id);
    }

}
