package com.baeldung.hexagonalarchitecture.application.rest;

import org.springframework.stereotype.Component;

import com.baeldung.hexagonalarchitecture.domain.Booking;

@Component
public class BookingMapper {

    public BookingDto toDto(Booking booking) {
        BookingDto dto = new BookingDto();
        dto.setStartDate(booking.getPeriodDetails()
            .getStartDate());
        dto.setEndDate(booking.getPeriodDetails()
            .getEndDate());
        dto.setFirstName(booking.getBookingName()
            .getFirstName());
        dto.setLastName(booking.getBookingName()
            .getLastName());
        dto.setUuid(booking.getUuid());
        return dto;
    }

    public Booking fromDto(BookingDto dto) {
        Booking.FullName fullName = new Booking.FullName(dto.getFirstName(), dto.getLastName());
        Booking.PeriodDetails period = new Booking.PeriodDetails(dto.getStartDate(), dto.getEndDate());
        if (dto.getUuid() != null) {
            return new Booking(dto.getUuid(), fullName, period);
        }
        return new Booking(fullName, period);
    }
}
