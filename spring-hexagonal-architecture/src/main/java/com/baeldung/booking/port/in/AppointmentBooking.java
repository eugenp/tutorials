package com.baeldung.booking.port.in;

import com.baeldung.booking.domain.model.AppointmentVO;
import com.baeldung.booking.domain.service.InvalidAppointmentException;

public interface AppointmentBooking {
    public long bookAppointment(AppointmentVO appointment) throws InvalidAppointmentException;
}
