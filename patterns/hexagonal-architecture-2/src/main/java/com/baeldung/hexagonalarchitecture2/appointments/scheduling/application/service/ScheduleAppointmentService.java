package com.baeldung.hexagonalarchitecture2.appointments.scheduling.application.service;

import com.baeldung.hexagonalarchitecture2.appointments.scheduling.application.port.inbound.CheckAppointmentTimeAvailableQuery;
import com.baeldung.hexagonalarchitecture2.appointments.scheduling.application.port.inbound.ScheduleAppointmentUseCase;
import com.baeldung.hexagonalarchitecture2.appointments.scheduling.application.port.outbound.CreateAppointmentPort;
import com.baeldung.hexagonalarchitecture2.appointments.scheduling.domain.Appointment;

public class ScheduleAppointmentService implements ScheduleAppointmentUseCase {
    private final CreateAppointmentPort createAppointmentPort;
    private final CheckAppointmentTimeAvailableQuery checkAppointmentTimeAvailableQuery;

    public ScheduleAppointmentService(CreateAppointmentPort createAppointmentPort, CheckAppointmentTimeAvailableQuery checkAppointmentTimeAvailableQuery) {
        this.createAppointmentPort = createAppointmentPort;
        this.checkAppointmentTimeAvailableQuery = checkAppointmentTimeAvailableQuery;
    }

    @Override
    public boolean scheduleAppointment(ScheduleAppointmentCommand command) {
        if (!checkAppointmentTimeAvailableQuery.isAppointmentAvailable(command.getTime())) {
            throw new AppointmentUnavailableException(command.getRequester(), command.getTime());
        }

        createAppointmentPort.createAppointment(new Appointment(command));
        return true;
    }
}
