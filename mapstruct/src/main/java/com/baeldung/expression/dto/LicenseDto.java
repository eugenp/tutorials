package com.baeldung.expression.dto;

import java.time.LocalDateTime;
import java.util.UUID;

public class LicenseDto {

    private UUID id;

    private LocalDateTime startDate;

    private LocalDateTime endDate;

    public UUID getId() {
        return id;
    }

    public LocalDateTime getStartDate() {
        return startDate;
    }

    public void setStartDate(LocalDateTime startDate) {
        this.startDate = startDate;
    }

    public LocalDateTime getEndDate() {
        return endDate;
    }

    public void setEndDate(LocalDateTime endDate) {
        this.endDate = endDate;
    }

}
