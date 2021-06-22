package com.baeldung.hexagonalarchitecture2.appointments.scheduling.adapter.inbound.web;

import com.baeldung.hexagonalarchitecture2.appointments.scheduling.application.port.inbound.ScheduleAppointmentUseCase;
import com.baeldung.hexagonalarchitecture2.appointments.scheduling.application.port.inbound.ScheduleAppointmentUseCase.ScheduleAppointmentCommand;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class ScheduleAppointmentController {
    private final ScheduleAppointmentUseCase scheduleAppointmentUseCase;

    public ScheduleAppointmentController(ScheduleAppointmentUseCase scheduleAppointmentUseCase) {
        this.scheduleAppointmentUseCase = scheduleAppointmentUseCase;
    }

    @PostMapping("/appointments")
    @ResponseStatus(HttpStatus.CREATED)
    public void scheduleAppointment(@RequestBody ScheduleAppointmentRequest request) {
        ScheduleAppointmentCommand command = new ScheduleAppointmentCommand(request.getRequestedTime(), request.getRequestedBy());
        scheduleAppointmentUseCase.scheduleAppointment(command);
    }
}
