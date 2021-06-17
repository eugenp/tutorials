package com.baeldung.booking.port.out;

import com.baeldung.booking.domain.model.AppointmentVO;

public interface AppointmentStorePort {

    long createAppointment(AppointmentVO appointment);

}
