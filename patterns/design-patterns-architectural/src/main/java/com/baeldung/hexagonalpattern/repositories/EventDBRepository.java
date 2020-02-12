package com.baeldung.hexagonalpattern.repositories;

import com.baeldung.hexagonalpattern.entities.Event;

import javax.persistence.EntityManager;
import javax.persistence.EntityTransaction;
import javax.persistence.Query;
import java.util.List;

public class EventDBRepository implements EventRepository {

    private EntityManager entityManager;

    public EventDBRepository(EntityManager entityManager) {
        this.entityManager = entityManager;
    }

    @Override
    public void saveEvent(Event event) {
        EntityTransaction transaction = this.entityManager.getTransaction();
        transaction.begin();
        Query insertQuery
            = this.entityManager.createNativeQuery("INSERT INTO events(id, name) VALUES(:eventId, :eventName)")
            .setParameter("eventId", event.getId())
            .setParameter("eventName", event.getName());
        insertQuery.executeUpdate();
        transaction.commit();
    }

    @Override
    public List<Event> getAllEvents() {
        Query selectAllQuery = this.entityManager.createNativeQuery("SELECT * FROM events", Event.class);
        return selectAllQuery.getResultList();
    }
}
