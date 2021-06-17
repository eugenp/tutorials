package com.baeldung.booking.domain.service;

import java.util.Optional;

import com.baeldung.booking.domain.model.AppointmentVO;
import com.baeldung.booking.port.in.AppointmentBooking;
import com.baeldung.booking.port.out.AppointmentQueryPort;
import com.baeldung.booking.port.out.AppointmentStorePort;

import org.springframework.stereotype.Service;

@Service
public class AppointmentBookingService implements AppointmentBooking {
    
    private AppointmentQueryPort appointmentQueryPort;
    private AppointmentStorePort appointmentStorePort;

    @Override
    public long bookAppointment(AppointmentVO appointment) throws InvalidAppointmentException {
        validateAppointment(appointment);
        long appointmentId = appointmentStorePort.createAppointment(appointment);
        return appointmentId;
    }

    private void validateAppointment(AppointmentVO appointment) throws InvalidAppointmentException {
        Optional<AppointmentVO> existingAppointment = appointmentQueryPort.getAppointmentsBetween(appointment.getWithUserName(), appointment.getFromTime(), appointment.getToTime());
        if(existingAppointment.isPresent()) {
            throw new InvalidAppointmentException("The appointment clashes with another appointment for the same user between "+existingAppointment.get().getFromTime() 
            + "to "+existingAppointment.get().getToTime());
        }
    }
}
