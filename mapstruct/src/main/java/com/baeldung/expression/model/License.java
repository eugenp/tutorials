package com.baeldung.expression.model;

import java.time.OffsetDateTime;
import java.util.UUID;

import lombok.Data;

@Data
public class License {

    private UUID id;

    private OffsetDateTime startDate;

    private OffsetDateTime endDate;

    private boolean active;

    private boolean renewalRequired;

}
