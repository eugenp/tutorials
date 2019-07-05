package com.baeldung.architecture.hexagonal;

import java.time.LocalDateTime;

public interface AttendanceUIPort {

    void registerAttendance(LocalDateTime in, LocalDateTime out);

}