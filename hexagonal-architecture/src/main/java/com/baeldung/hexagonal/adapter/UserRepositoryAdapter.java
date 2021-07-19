package com.baeldung.hexagonal.adapter;

import com.baeldung.hexagonal.domain.User;
import com.baeldung.hexagonal.port.UserRepositoryPort;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.transaction.Transactional;

@Repository
public class UserRepositoryAdapter implements UserRepositoryPort {
    @PersistenceContext
    private EntityManager entityManager;

    @Override
    @Transactional
    public void add(User user) {
        entityManager.persist(user);
    }

    @Override
    public User getDetail(String userId) {
        return entityManager.find(User.class, userId);
    }
}
