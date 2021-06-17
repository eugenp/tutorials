package com.baeldung.booking.domain.model;

import java.time.LocalDateTime;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public class AppointmentVO {
    String withUserName;
    LocalDateTime fromTime;
    LocalDateTime toTime;
}
