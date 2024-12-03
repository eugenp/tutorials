package com.baeldung.hibernate.timezonestorage;

import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
interface AstronomicalObservationRepository extends JpaRepository<AstronomicalObservation, UUID> {
}