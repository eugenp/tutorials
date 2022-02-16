package com.baeldung.hexagonalarchitecture.domain;

public interface HotelRoomRepository {
    HotelRoom findByRoomNumber(String flightNumber);

    void save(HotelRoom flight);
}
