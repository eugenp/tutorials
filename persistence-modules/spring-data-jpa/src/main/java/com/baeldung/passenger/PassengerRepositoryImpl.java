package com.baeldung.passenger;

import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.util.List;

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
