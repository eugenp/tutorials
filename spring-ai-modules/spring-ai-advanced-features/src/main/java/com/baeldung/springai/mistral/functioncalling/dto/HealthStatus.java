package com.baeldung.springai.mistral.functioncalling.dto;

import java.time.LocalDate;

public record HealthStatus(String status, LocalDate changeDate) {
}
