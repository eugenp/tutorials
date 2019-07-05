package com.baeldung.architecture.hexagonal;

import java.time.LocalDateTime;

public class AttendanceUIAdapter implements AttendanceUIPort {

    @Override
    public void registerAttendance(LocalDateTime in, LocalDateTime out) {
        AttendanceService.registerAttendance(in, out);
    }
}
