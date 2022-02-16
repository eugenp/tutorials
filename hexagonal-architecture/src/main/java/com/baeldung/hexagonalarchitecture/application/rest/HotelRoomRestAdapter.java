package com.baeldung.hexagonalarchitecture.application.rest;

import java.util.stream.Collectors;

import javax.validation.ConstraintViolation;
import javax.validation.Validator;

import org.springframework.stereotype.Component;

import com.baeldung.hexagonalarchitecture.domain.Booking;
import com.baeldung.hexagonalarchitecture.domain.HotelRoomBookingService;

@Component
public class HotelRoomRestAdapter {

    private HotelRoomBookingService hotelRoomBookingService;
    private BookingMapper bookingMapper;
    private Validator validator;

    public HotelRoomRestAdapter(HotelRoomBookingService hotelRoomBookingService, BookingMapper bookingMapper, Validator validator) {
        this.hotelRoomBookingService = hotelRoomBookingService;
        this.bookingMapper = bookingMapper;
        this.validator = validator;
    }

    public BookingDto addBooking(String roomNumber, BookingDto dto) {
        validate(dto);
        Booking booking = bookingMapper.fromDto(dto);
        Booking updatedBooking = hotelRoomBookingService.addBooking(roomNumber, booking);
        return bookingMapper.toDto(updatedBooking);
    }

    private void validate(BookingDto dto) {
        String errorMessage = validator.validate(dto)
            .stream()
            .map(ConstraintViolation::getMessage)
            .collect(Collectors.joining("; "));
        if (!errorMessage.isBlank()) {
            throw new IllegalArgumentException(errorMessage);
        }
    }

    public void performOperation(String roomNumber, RoomOperation operation) {
        switch (operation) {
        case CLEANING:
            hotelRoomBookingService.startHospitalityCleaning(roomNumber);
            break;
        case MAINTENANCE:
            hotelRoomBookingService.startRoutineMaintenance(roomNumber);
            break;
        default:
            throw new IllegalArgumentException(String.format("The operation %s not available.", roomNumber));
        }
    }
}
