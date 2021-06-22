package com.baeldung.hexagonalarchitecture2.appointments.scheduling.application.port.inbound;

import java.time.LocalDateTime;

public interface CheckAppointmentTimeAvailableQuery {

    boolean isAppointmentAvailable(LocalDateTime appointmentTime);

}
