package com.baeldung.hexagonalarchitecture2.appointments.scheduling.application.port.outbound;

import com.baeldung.hexagonalarchitecture2.appointments.scheduling.domain.Appointment;

public interface CreateAppointmentPort {
    void createAppointment(Appointment appointment);
}
