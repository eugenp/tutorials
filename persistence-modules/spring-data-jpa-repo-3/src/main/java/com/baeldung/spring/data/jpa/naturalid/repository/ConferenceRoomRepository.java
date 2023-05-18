package com.baeldung.spring.data.jpa.naturalid.repository;

import com.baeldung.spring.data.jpa.naturalid.entity.ConferenceRoom;

import org.springframework.stereotype.Repository;

@Repository
public interface ConferenceRoomRepository extends NaturalIdRepository<ConferenceRoom, String> {
}
