package com.baeldung.hexagonalarchitecture2.appointments.scheduling.application.service;

import com.baeldung.hexagonalarchitecture2.appointments.scheduling.application.port.inbound.CheckAppointmentTimeAvailableQuery;
import com.baeldung.hexagonalarchitecture2.appointments.scheduling.application.port.inbound.ScheduleAppointmentUseCase.ScheduleAppointmentCommand;
import com.baeldung.hexagonalarchitecture2.appointments.scheduling.application.port.outbound.CreateAppointmentPort;
import com.baeldung.hexagonalarchitecture2.appointments.scheduling.domain.Appointment;
import com.github.javafaker.Faker;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.time.LocalDateTime;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.any;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.verifyNoMoreInteractions;
import static org.mockito.Mockito.when;

class ScheduleAppointmentServiceUnitTest {
    static final Faker faker = Faker.instance();

    ScheduleAppointmentService subject;
    CreateAppointmentPort mockCreateAppointmentPort = mock(CreateAppointmentPort.class);
    CheckAppointmentTimeAvailableQuery mockCheckAppointmentTimeAvailableQuery = mock(CheckAppointmentTimeAvailableQuery.class);

    @BeforeEach
    void setup() {
        subject = new ScheduleAppointmentService(mockCreateAppointmentPort, mockCheckAppointmentTimeAvailableQuery);
    }

    @AfterEach
    void tearDown() {
        verifyNoMoreInteractions(mockCreateAppointmentPort);
    }

    @Test
    void givenAppointmentTimeIsAvailable_thenScheduleAppointmentSucceeds() {
        var command = ScheduleAppointmentCommand.builder().time(LocalDateTime.now()).requester(faker.internet().emailAddress()).build();
        when(mockCheckAppointmentTimeAvailableQuery.isAppointmentAvailable(command.getTime())).thenReturn(true);

        assertThat(subject.scheduleAppointment(command)).isTrue();
        verify(mockCreateAppointmentPort).createAppointment(new Appointment(command));
    }

    @Test
    void givenAppointmentTimeIsUnavailable_thenScheduleAppointmentFails() {
        var command = ScheduleAppointmentCommand.builder().time(LocalDateTime.now()).requester(faker.internet().emailAddress()).build();
        when(mockCheckAppointmentTimeAvailableQuery.isAppointmentAvailable(command.getTime())).thenReturn(false);

        assertThrows(AppointmentUnavailableException.class, () -> {
            subject.scheduleAppointment(command);
        });
        verify(mockCreateAppointmentPort, never()).createAppointment(any());
    }
}