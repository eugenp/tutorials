package com.baeldung.springai.mcp.test;

import java.util.Map;

public record ExchangeRateResponse(double amount, String base, String date, Map<String, Double> rates) {
}