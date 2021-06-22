package com.baeldung.hexagonalarchitecture2.appointments.scheduling.adapter.outbound.persistence;

import com.baeldung.hexagonalarchitecture2.appointments.scheduling.application.port.inbound.CheckAppointmentTimeAvailableQuery;
import com.baeldung.hexagonalarchitecture2.appointments.scheduling.application.port.outbound.CreateAppointmentPort;
import com.baeldung.hexagonalarchitecture2.appointments.scheduling.domain.Appointment;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;

@Component
public class AppointmentPersistenceAdapter implements CheckAppointmentTimeAvailableQuery, CreateAppointmentPort {
    private final AppointmentRepository appointmentRepository;

    public AppointmentPersistenceAdapter(AppointmentRepository appointmentRepository) {
        this.appointmentRepository = appointmentRepository;
    }

    @Override
    public boolean isAppointmentAvailable(LocalDateTime appointmentTime) {
        return !appointmentRepository.existsByAppointmentTime(appointmentTime);
    }

    @Override
    public void createAppointment(Appointment appointment) {
        AppointmentEntity appointmentEntity = AppointmentEntity.builder()
          .appointmentTime(appointment.getTime())
          .requesterEmail(appointment.getRequester())
          .build();
        appointmentRepository.save(appointmentEntity);
    }
}
