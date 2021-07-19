package com.baeldung.oauth2.authorization.server.model;

import javax.enterprise.context.ApplicationScoped;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.transaction.Transactional;

@ApplicationScoped
public class AppDataRepository {

    @PersistenceContext
    private EntityManager entityManager;

    public Client getClient(String clientId) {
        return entityManager.find(Client.class, clientId);
    }

    public User getUser(String userId) {
        return entityManager.find(User.class, userId);
    }

    @Transactional
    public AuthorizationCode save(AuthorizationCode authorizationCode) {
        entityManager.persist(authorizationCode);
        return authorizationCode;
    }
}
