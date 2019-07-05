package com.baeldung.architecture.hexagonal;

import java.time.LocalDateTime;

public interface PersistencePort {

    void saveAttendance(LocalDateTime in, LocalDateTime out, String status);

}
