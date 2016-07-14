package com.baeldung.spring43.composedmapping;

import java.util.Map;

public interface AppointmentService {

    Map<String, Appointment> getAppointmentsForToday();

}
