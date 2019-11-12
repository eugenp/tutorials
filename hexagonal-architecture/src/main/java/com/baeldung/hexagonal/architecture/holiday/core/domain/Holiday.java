package com.baeldung.hexagonal.architecture.holiday.core.domain;

import lombok.Builder;
import lombok.Getter;

import java.time.LocalDateTime;
import java.util.UUID;

@Builder
@Getter
public class Holiday {

    private UUID id;
    private UUID employeeId;
    private LocalDateTime from;
    private LocalDateTime to;
    // another fields
}
