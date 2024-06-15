package com.baeldung.spring.ai.dto;

import java.time.LocalDate;

public record HealthStatus(String status, LocalDate changeDate) {
}
