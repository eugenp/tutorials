package com.baeldung.booking.app.rest.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class AppointmentDTO {
    String withUser;
    String dayOfMonth;
    String fromHour;
    String toHour;
    String fromMinute;
    String toMinute;
}
