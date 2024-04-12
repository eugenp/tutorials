package com.baeldung.spring.data.jpa.naturalid;

import com.baeldung.spring.data.jpa.naturalid.entity.ConferenceRoom;
import com.baeldung.spring.data.jpa.naturalid.entity.GuestRoom;

import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;

import org.hibernate.Session;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class HotelRoomsService {

    private final EntityManager entityManager;

    public HotelRoomsService(EntityManagerFactory entityManagerFactory) {
        this.entityManager = entityManagerFactory.createEntityManager();
    }

    public Optional<ConferenceRoom> conferenceRoom(String name) {
        Session session = entityManager.unwrap(Session.class);
        return session.bySimpleNaturalId(ConferenceRoom.class)
            .loadOptional(name);
    }

    public Optional<GuestRoom> guestRoom(int roomNumber, int floor) {
        Session session = entityManager.unwrap(Session.class);
        return session.byNaturalId(GuestRoom.class)
            .using("roomNumber", roomNumber)
            .using("floor", floor)
            .loadOptional();
    }

}
