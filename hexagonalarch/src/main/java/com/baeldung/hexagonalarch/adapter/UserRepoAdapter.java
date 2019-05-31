package com.baeldung.hexagonalarch.adapter;

import com.baeldung.hexagonalarch.model.User;
import com.baeldung.hexagonalarch.port.UserRepoPort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

@Service
public class UserRepoAdapter implements UserRepoPort {

    @PersistenceContext
    private EntityManager entityManager;

    @Transactional
    @Override
    public void create(User user) {
        entityManager.persist(user);
    }

    @Override
    public User getUser(Integer userid) {
        return entityManager.find(User.class, userid);
    }
}
