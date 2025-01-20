package com.baeldung.caching.ttl.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.baeldung.caching.ttl.model.Hotel;

public interface HotelRepository extends JpaRepository<Hotel, Long> {

    default List<Hotel> getAllHotels() {
        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
        return findAll();
    }
}