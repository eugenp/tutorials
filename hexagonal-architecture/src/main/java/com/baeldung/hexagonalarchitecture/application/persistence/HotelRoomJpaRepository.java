package com.baeldung.hexagonalarchitecture.application.persistence;

import org.springframework.data.jpa.repository.JpaRepository;

public interface HotelRoomJpaRepository extends JpaRepository<JpaHotelRoom, String> {
}
