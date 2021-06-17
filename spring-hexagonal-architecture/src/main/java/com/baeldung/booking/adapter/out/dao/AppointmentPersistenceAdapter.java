package com.baeldung.booking.adapter.out.dao;

import java.time.LocalDateTime;
import java.time.ZoneOffset;
import java.util.Optional;

import com.baeldung.booking.domain.model.AppointmentVO;
import com.baeldung.booking.port.out.AppointmentQueryPort;
import com.baeldung.booking.port.out.AppointmentStorePort;

public class AppointmentPersistenceAdapter implements AppointmentQueryPort, AppointmentStorePort{

    private AppointmentRepository appointmentRepository;

    @Override
    public long createAppointment(AppointmentVO appointment) {
        AppointmentEntity appointmentEntity = 
        AppointmentEntity.builder().withUser(appointment.getWithUserName())
        .fromTime(appointment.getFromTime().toEpochSecond(ZoneOffset.UTC))
        .toTime(appointment.getToTime().toEpochSecond(ZoneOffset.UTC))
        .build();
        return appointmentRepository.save(appointmentEntity).getId();
    }

    @Override
    public Optional<AppointmentVO> getAppointmentsBetween(String withUserName, LocalDateTime fromTime,
            LocalDateTime toTime) {
        
        return null;
    }
    
}
