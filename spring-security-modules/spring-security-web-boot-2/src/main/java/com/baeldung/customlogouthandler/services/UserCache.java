package com.baeldung.customlogouthandler.services;

import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import org.springframework.stereotype.Service;

import com.baeldung.customlogouthandler.user.User;

@Service
public class UserCache {

    @PersistenceContext
    private EntityManager entityManager;

    private final ConcurrentMap<String, User> store = new ConcurrentHashMap<>(256);

    public User getByUserName(String userName) {
        return store.computeIfAbsent(userName, k -> entityManager.createQuery("from User where login=:login", User.class)
            .setParameter("login", k)
            .getSingleResult());
    }

    public void evictUser(String userName) {
        store.remove(userName);
    }

    public int size() {
        return store.size();
    }

}
