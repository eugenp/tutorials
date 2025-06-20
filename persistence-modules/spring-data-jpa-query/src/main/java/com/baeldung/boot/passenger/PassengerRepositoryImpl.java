package com.baeldung.boot.passenger;

import java.util.List;

import org.springframework.stereotype.Repository;

import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;

@Repository
class PassengerRepositoryImpl implements CustomPassengerRepository {

    @PersistenceContext
    private EntityManager entityManager;

    @Override
    public List<Passenger> findOrderedBySeatNumberLimitedTo(int limit) {
        return entityManager.createQuery("SELECT p FROM Passenger p ORDER BY p.seatNumber",
          Passenger.class).setMaxResults(limit).getResultList();
    }
}
