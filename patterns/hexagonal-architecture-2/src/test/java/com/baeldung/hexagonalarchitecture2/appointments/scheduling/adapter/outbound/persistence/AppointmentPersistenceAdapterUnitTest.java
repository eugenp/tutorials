package com.baeldung.hexagonalarchitecture2.appointments.scheduling.adapter.outbound.persistence;

import com.baeldung.hexagonalarchitecture2.appointments.scheduling.domain.Appointment;
import com.github.javafaker.Faker;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentCaptor;

import java.time.LocalDateTime;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

class AppointmentPersistenceAdapterUnitTest {
    static final Faker faker = Faker.instance();

    AppointmentPersistenceAdapter subject;
    AppointmentRepository mockAppointmentRepository = mock(AppointmentRepository.class);

    @BeforeEach
    void setup() {
        subject = new AppointmentPersistenceAdapter(mockAppointmentRepository);
    }

    @Test
    void isAppointmentAvailable_whenAppointmentExists() {
        var appointmentTime = LocalDateTime.now();
        when(mockAppointmentRepository.existsByAppointmentTime(appointmentTime)).thenReturn(true);

        assertThat(subject.isAppointmentAvailable(appointmentTime)).isFalse();
    }

    @Test
    void isAppointmentAvailable_whenAppointmentDoesNotExist() {
        var appointmentTime = LocalDateTime.now();
        when(mockAppointmentRepository.existsByAppointmentTime(appointmentTime)).thenReturn(false);

        assertThat(subject.isAppointmentAvailable(appointmentTime)).isTrue();
    }

    @Test
    void createAppointmentSavesToTheRepository() {
        var arguments = ArgumentCaptor.forClass(AppointmentEntity.class);
        var appointmentToCreate = Appointment.builder().requester(faker.internet().emailAddress()).time(LocalDateTime.now().plusDays(10)).build();
        subject.createAppointment(appointmentToCreate);

        verify(mockAppointmentRepository).save(arguments.capture());

        var savedAppointmentEntity = arguments.getValue();
        assertThat(savedAppointmentEntity.getAppointmentTime()).isEqualTo(appointmentToCreate.getTime());
        assertThat(savedAppointmentEntity.getRequesterEmail()).isEqualTo(appointmentToCreate.getRequester());
    }
}