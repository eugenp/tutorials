package com.baeldung.hexagonalarchitecture2.appointments.scheduling.adapter.outbound.persistence;

import org.springframework.data.repository.CrudRepository;

import java.time.LocalDateTime;

public interface AppointmentRepository extends CrudRepository<AppointmentEntity, String> {
    boolean existsByAppointmentTime(LocalDateTime appointmentTime);
}
