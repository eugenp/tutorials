package com.baeldung.dependency.injectiontypes;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class HotelService {

    private final RoomService roomService;

    @Autowired
    public HotelService(RoomService roomService) {
        this.roomService = roomService;
    }

    public String print() {
        return "Hello from Hotel Service";
    }

    public RoomService getRoomService() {
        return roomService;
    }
}
