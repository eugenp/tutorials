package com.baeldung.jpa.returnmultipleentities;

import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.Persistence;
import jakarta.persistence.Query;
import java.util.List;

public class ReportRepository {
    private final EntityManagerFactory emf;

    public ReportRepository() {
        emf = Persistence.createEntityManagerFactory("jpa-h2-return-multiple-entities");
    }

    public List<Object[]> find(String email) {
        EntityManager entityManager = emf.createEntityManager();
        Query query = entityManager.createQuery("SELECT c, s, u FROM  Channel c, Subscription s, Users u WHERE c.subscriptionId = s.id AND s.id = u.subscriptionId AND u.email=:email");
        query.setParameter("email", email);

        return query.getResultList();
    }
}
