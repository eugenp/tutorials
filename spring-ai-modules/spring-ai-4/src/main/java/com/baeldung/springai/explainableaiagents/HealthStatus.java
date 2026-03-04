package com.baeldung.springai.explainableaiagents;

import java.time.LocalDate;

public record HealthStatus(String status, LocalDate changeDate) {
}
