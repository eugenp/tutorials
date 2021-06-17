package com.baeldung.booking.port.out;

import java.time.LocalDateTime;
import java.util.Optional;

import com.baeldung.booking.domain.model.AppointmentVO;

public interface AppointmentQueryPort {

    Optional<AppointmentVO> getAppointmentsBetween(String withUserName, LocalDateTime fromTime, LocalDateTime toTime);

}
