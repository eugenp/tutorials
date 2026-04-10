package com.baeldung.springai.mcp.test;

import org.springaicommunity.mcp.annotation.McpTool;
import org.springaicommunity.mcp.annotation.McpToolParam;
import org.springframework.stereotype.Component;

@Component
public class ExchangeRateMcpTool {

    private final ExchangeRateService exchangeRateService;

    public ExchangeRateMcpTool(ExchangeRateService exchangeRateService) {
        this.exchangeRateService = exchangeRateService;
    }

    @McpTool(description = "Get the latest exchange rates for a base currency")
    public ExchangeRateResponse getLatestExchangeRate(
        @McpToolParam(description = "Base currency code, e.g. GBP, USD, EUR", required = true) String base) {
        return exchangeRateService.getLatestExchangeRate(base);
    }

}
