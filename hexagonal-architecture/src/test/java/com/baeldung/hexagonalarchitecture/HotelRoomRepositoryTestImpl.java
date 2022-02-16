package com.baeldung.hexagonalarchitecture;

import java.util.HashMap;
import java.util.Map;

import com.baeldung.hexagonalarchitecture.domain.HotelRoom;
import com.baeldung.hexagonalarchitecture.domain.HotelRoomRepository;

public class HotelRoomRepositoryTestImpl implements HotelRoomRepository {
    private Map<String, HotelRoom> rooms = new HashMap<>();

    @Override
    public HotelRoom findByRoomNumber(String roomNumber) {
        return rooms.getOrDefault(roomNumber, null);
    }

    @Override
    public void save(HotelRoom room) {
        rooms.put(room.getRoomNumber(), room);
    }
}
