package com.baeldung.jpa.returnmultipleentities;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import javax.persistence.Query;
import java.util.List;

public class ReportRepository {
    private final EntityManagerFactory emf;

    public ReportRepository() {
        emf = Persistence.createEntityManagerFactory("jpa-h2-return-multiple-entities");
    }

    public List<Object[]> find(String email) {
        EntityManager entityManager = emf.createEntityManager();
        Query query = entityManager.createQuery("SELECT c, s, u FROM  Channel c, Subscription s, User u WHERE c.subscriptionId = s.id AND s.id = u.subscriptionId AND u.email=:email");
        query.setParameter("email", email);

        return query.getResultList();
    }
}
