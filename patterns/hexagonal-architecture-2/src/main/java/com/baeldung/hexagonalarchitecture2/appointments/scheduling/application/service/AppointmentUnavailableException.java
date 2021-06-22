package com.baeldung.hexagonalarchitecture2.appointments.scheduling.application.service;

import java.time.LocalDateTime;

public class AppointmentUnavailableException extends RuntimeException {
    public AppointmentUnavailableException(String requester, LocalDateTime appointmentTime) {
        super(String.format("Appointment for %s at time %s is unavailable.", requester, appointmentTime));
    }
}
