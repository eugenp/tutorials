package com.baeldung.hexagonalarchitecture2.appointments.scheduling.application.port.inbound;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.RequiredArgsConstructor;

import java.time.LocalDateTime;

public interface ScheduleAppointmentUseCase {

    boolean scheduleAppointment(ScheduleAppointmentCommand command);

    @Getter
    @Builder
    @NoArgsConstructor
    @AllArgsConstructor
    @RequiredArgsConstructor
    class ScheduleAppointmentCommand {
        private LocalDateTime time;
        private String requester;
    }
}
