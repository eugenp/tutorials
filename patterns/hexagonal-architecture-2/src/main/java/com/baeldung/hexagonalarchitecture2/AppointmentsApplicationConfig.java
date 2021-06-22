package com.baeldung.hexagonalarchitecture2;

import com.baeldung.hexagonalarchitecture2.appointments.scheduling.application.port.inbound.CheckAppointmentTimeAvailableQuery;
import com.baeldung.hexagonalarchitecture2.appointments.scheduling.application.port.inbound.ScheduleAppointmentUseCase;
import com.baeldung.hexagonalarchitecture2.appointments.scheduling.application.port.outbound.CreateAppointmentPort;
import com.baeldung.hexagonalarchitecture2.appointments.scheduling.application.service.ScheduleAppointmentService;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class AppointmentsApplicationConfig {

    @Bean
    public ScheduleAppointmentUseCase scheduleAppointmentUseCase(CreateAppointmentPort createAppointmentPort, CheckAppointmentTimeAvailableQuery checkAppointmentTimeAvailableQuery) {
        return new ScheduleAppointmentService(createAppointmentPort, checkAppointmentTimeAvailableQuery);
    }
}
