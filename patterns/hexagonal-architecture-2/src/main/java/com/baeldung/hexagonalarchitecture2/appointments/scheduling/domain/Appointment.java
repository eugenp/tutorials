package com.baeldung.hexagonalarchitecture2.appointments.scheduling.domain;

import com.baeldung.hexagonalarchitecture2.appointments.scheduling.application.port.inbound.ScheduleAppointmentUseCase.ScheduleAppointmentCommand;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Appointment {
    private String id;
    private LocalDateTime time;
    private String requester;

    public Appointment(ScheduleAppointmentCommand command) {
        this("", command.getTime(), command.getRequester());
    }
}
