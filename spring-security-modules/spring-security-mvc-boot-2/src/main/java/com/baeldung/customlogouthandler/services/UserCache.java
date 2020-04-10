package com.baeldung.customlogouthandler.services;

import com.baeldung.customlogouthandler.user.User;
import org.springframework.stereotype.Service;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;

@Service
public class UserCache {

    @PersistenceContext
    private EntityManager entityManager;

    private final ConcurrentMap<String, User> store = new ConcurrentHashMap<>(256);

    public User getByLogin(String login) {
        return store.computeIfAbsent(login, k -> entityManager.createQuery("from User where login=:login", User.class)
                .setParameter("login", k)
                .getSingleResult());
    }

    public void evictUser(String login) {
        store.remove(login);
    }

    public int size() {
        return this.store.size();
    }

}
