package com.baeldung.jpa.defaultvalues;

import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.Persistence;
import jakarta.persistence.Query;

public class UserEntityRepository {

    private EntityManagerFactory emf = null;

    public UserEntityRepository() {
        emf = Persistence.createEntityManagerFactory("entity-default-values");
    }

    public UserEntity find(Long id) {
        EntityManager entityManager = emf.createEntityManager();
        UserEntity user = entityManager.find(UserEntity.class, id);
        entityManager.close();
        return user;
    }

    public void save(UserEntity user, Long id) {
        user.setId(id);

        EntityManager entityManager = emf.createEntityManager();
        entityManager.getTransaction()
            .begin();
        entityManager.persist(user);
        entityManager.getTransaction()
            .commit();
        entityManager.close();
    }

    public void saveEntity(UserEntity user, Long id) {
        EntityManager entityManager = emf.createEntityManager();
        entityManager.getTransaction()
            .begin();

        Query query = entityManager.createNativeQuery("INSERT INTO users_entity (id) VALUES(?) ");
        query.setParameter(1, id);
        query.executeUpdate();
        entityManager.getTransaction()
            .commit();
        entityManager.close();
    }

    public void clean() {
        emf.close();
    }
}