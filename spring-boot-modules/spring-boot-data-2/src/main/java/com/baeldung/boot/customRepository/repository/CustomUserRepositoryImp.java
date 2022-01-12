package com.baeldung.boot.customRepository.repository;

import java.util.Objects;

import javax.annotation.PostConstruct;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import com.baeldung.boot.customRepository.model.User;

public class CustomUserRepositoryImp implements CustomUserRepository {

    @PersistenceContext
    private EntityManager entityManager;

    @Override
    public User customFindMethod(Long id) {
        return (User) entityManager.createQuery("from User u where u.id = :id")
            .setParameter("id", id)
            .getSingleResult();
    }

    @PostConstruct
    public void postConstruct() {
        Objects.requireNonNull(entityManager);
    }
}
