package com.baeldung.springai.mcp.oauth2;

import org.springframework.ai.tool.annotation.Tool;
import org.springframework.ai.tool.annotation.ToolParam;

public class StockInformationHolder {
    @Tool(description = "Get stock price for a company symbol")
    public String getStockPrice(@ToolParam String symbol) {
        if ("AAPL".equalsIgnoreCase(symbol)) {
            return "AAPL: $150.00";
        } else if ("GOOGL".equalsIgnoreCase(symbol)) {
            return "GOOGL: $2800.00";
        } else {
            return symbol + ": Data not available";
        }
    }
}
