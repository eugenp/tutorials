package com.baeldung.hexagonalarchitecture.application.rest;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("api/rooms")
public class HotelRoomController {

    private HotelRoomRestAdapter hotelRoomRestAdapter;

    public HotelRoomController(HotelRoomRestAdapter adapter) {
        this.hotelRoomRestAdapter = adapter;
    }

    @PostMapping("{roomNumber}/bookings")
    public BookingDto addBooking(@PathVariable String roomNumber, @RequestBody BookingDto dto) {
        return hotelRoomRestAdapter.addBooking(roomNumber, dto);
    }

    @PatchMapping("{roomNumber}")
    public void changeRoomStatus(@PathVariable String roomNumber, @RequestParam RoomOperation operation) {
        hotelRoomRestAdapter.performOperation(roomNumber, operation);
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity handleError(Exception ex) {
        return ResponseEntity.badRequest()
            .body(ex.getMessage());
    }
}
