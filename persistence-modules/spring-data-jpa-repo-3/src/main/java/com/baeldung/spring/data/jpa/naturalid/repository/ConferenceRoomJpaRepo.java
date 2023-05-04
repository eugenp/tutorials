package com.baeldung.spring.data.jpa.naturalid.repository;

import com.baeldung.spring.data.jpa.naturalid.entity.ConferenceRoom;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ConferenceRoomJpaRepo extends JpaRepository<ConferenceRoom, Long> {
}
