package com.baeldung.booking.adapter.in.rest;

import java.time.LocalDateTime;

import com.baeldung.booking.app.rest.dto.AppointmentDTO;
import com.baeldung.booking.domain.model.AppointmentVO;
import com.baeldung.booking.domain.service.InvalidAppointmentException;
import com.baeldung.booking.port.in.AppointmentBooking;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class AppointmentBookingRestController {
    
    @Autowired
    private AppointmentBooking appointmentBooking;

    @PostMapping("/appointment/book")
    public long bookAppointment(@RequestBody AppointmentDTO appointmentDto) throws InvalidAppointmentException {
        AppointmentVO appointment = mapAppointment(appointmentDto);
        return appointmentBooking.bookAppointment(appointment);
    }

    /**
     * Validates and 'adapts' the appointment to be sent to the 'in' port
     * Note that the validation is non-business validate
     */
    private AppointmentVO mapAppointment(AppointmentDTO appointmentDto)throws InvalidAppointmentException {
        LocalDateTime currentTime = LocalDateTime.now();
        LocalDateTime fromTime = getTime(currentTime, appointmentDto.getDayOfMonth(), appointmentDto.getFromHour(), appointmentDto.getFromMinute());
        if(fromTime.isBefore(currentTime)) {
            throw new InvalidAppointmentException("From time cannot be before the current time");
        }
        LocalDateTime toTime = getTime(currentTime, appointmentDto.getDayOfMonth(), appointmentDto.getToHour(), appointmentDto.getToMinute());
        if(toTime.isBefore(currentTime) || toTime.isBefore(fromTime)) {
            throw new InvalidAppointmentException("To time cannot be before the current time or before the from time");
        }
        return new AppointmentVO(appointmentDto.getWithUser(), fromTime, toTime);
    }

    private LocalDateTime getTime(LocalDateTime currentTime, String dayOfMonth, String hour, String minute) {
        return LocalDateTime.of(currentTime.getYear(), currentTime.getMonth(), Integer.valueOf(dayOfMonth), 
                                                    Integer.valueOf(hour), Integer.valueOf(minute));
    }
}
