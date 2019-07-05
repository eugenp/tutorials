package com.baeldung.architecture.hexagonal;

import java.time.Duration;
import java.time.LocalDateTime;

public class AttendanceService {

    private static PersistencePort persistencePort = new OraclePersistenceAdapter();

    public static void registerAttendance(LocalDateTime in, LocalDateTime out) {
        String attendanceStatus = "On Leave";
        Duration duration = Duration.between(out, in);
        long presentHours = duration.toHours();
        if (presentHours > 6) {
            attendanceStatus = "Present";
        } else if (presentHours > 3) {
            attendanceStatus = "Half-day off";
        }
        persistencePort.saveAttendance(in, out, attendanceStatus);
    }

}