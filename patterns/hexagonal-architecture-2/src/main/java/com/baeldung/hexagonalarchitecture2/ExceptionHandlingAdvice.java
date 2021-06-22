package com.baeldung.hexagonalarchitecture2;

import com.baeldung.hexagonalarchitecture2.appointments.scheduling.application.service.AppointmentUnavailableException;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;

@ControllerAdvice
public class ExceptionHandlingAdvice {

    @ResponseStatus(HttpStatus.CONFLICT)
    @ExceptionHandler(AppointmentUnavailableException.class)
    void handleConflict() {

    }
}
