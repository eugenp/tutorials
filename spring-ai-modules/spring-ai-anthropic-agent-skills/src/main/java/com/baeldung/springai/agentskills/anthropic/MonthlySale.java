package com.baeldung.springai.agentskills.anthropic;

import java.math.BigDecimal;
import java.time.Month;

public record MonthlySale(String product, int year, Month month, BigDecimal amount) {
}
