package com.baeldung.caching.ttl.repository;

import com.baeldung.caching.ttl.model.Hotel;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface HotelRepository extends JpaRepository<Hotel, Long> {

    default List<Hotel> getAllHotels() {
        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
        return findAll();
    }

    default Optional<Hotel> getHotelById(Long hotelId) {
        try {
            Thread.sleep(500);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
        return findById(hotelId);
    }
}