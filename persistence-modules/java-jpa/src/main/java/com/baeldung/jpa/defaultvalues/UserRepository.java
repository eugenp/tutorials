package com.baeldung.jpa.defaultvalues;

import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.Persistence;

public class UserRepository {

    private EntityManagerFactory emf = null;

    public UserRepository() {
        emf = Persistence.createEntityManagerFactory("entity-default-values");
    }

    public User find(Long id) {
        EntityManager entityManager = emf.createEntityManager();
        User user = entityManager.find(User.class, id);
        entityManager.close();
        return user;
    }

    public void save(User user, Long id) {
        user.setId(id);

        EntityManager entityManager = emf.createEntityManager();
        entityManager.getTransaction().begin();
        entityManager.persist(user);
        entityManager.getTransaction().commit();
        entityManager.close();
    }

    public void clean() {
        emf.close();
    }

}
