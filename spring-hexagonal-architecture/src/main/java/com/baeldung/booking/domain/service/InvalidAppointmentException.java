package com.baeldung.booking.domain.service;

import lombok.AllArgsConstructor;

@AllArgsConstructor
public class InvalidAppointmentException extends Exception {
    String message;
}
