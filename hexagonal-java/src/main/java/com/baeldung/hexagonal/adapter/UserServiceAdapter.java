package com.baeldung.hexagonal.adapter;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import com.baeldung.domain.User;
import com.baeldung.hexagonal.port.UserDBPort;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class UserServiceAdapter implements UserDBPort {

    @PersistenceContext
    private EntityManager entityManager;

    @Transactional
    @Override
    public void register(String name) {
        User user = new User();
        user.setName(name);

        entityManager.persist(user);
    }

    @Override
    public User getUser(Integer id) {
        return entityManager.find(User.class, id);
    }
    
}
