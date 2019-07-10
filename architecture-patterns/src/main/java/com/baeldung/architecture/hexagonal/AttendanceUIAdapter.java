package com.baeldung.architecture.hexagonal;

import java.time.LocalDateTime;

public class AttendanceUIAdapter implements AttendanceUIPort {

    private AttendanceService attendanceService = new AttendanceService();

    @Override
    public void registerAttendance(LocalDateTime in, LocalDateTime out) {
        attendanceService.registerAttendance(in, out);
    }
}
