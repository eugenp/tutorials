package com.baeldung.architecture.hexagonal;

import java.time.LocalDateTime;

public class OraclePersistenceAdapter implements PersistencePort {

    @Override
    public void saveAttendance(LocalDateTime in, LocalDateTime out, String status) {
        // Logic required to save record to database
    }
}
