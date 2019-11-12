package com.baeldung.hexagonal.outer;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import javax.persistence.TypedQuery;
import java.util.List;
import java.util.Optional;

public class IpLocations {

    private EntityManager entityManager;

    public IpLocations() {
        EntityManagerFactory entityManagerFactory = Persistence.createEntityManagerFactory("user-unit");
        entityManager = entityManagerFactory.createEntityManager();
    }

    public Optional<IpLocation> findByIp(String ip) {
        String qs = "SELECT l FROM IpLocation l WHERE l.ip = :ip";
        TypedQuery<IpLocation> query = entityManager.createQuery(qs, IpLocation.class);
        List<IpLocation> locations = query.setParameter("ip", ip).getResultList();
        IpLocation result = locations.size() > 0 ? locations.get(0) : null;
        return Optional.ofNullable(result);
    }
}