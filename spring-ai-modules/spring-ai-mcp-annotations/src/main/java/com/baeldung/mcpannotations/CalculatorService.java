package com.baeldung.mcpannotations;

import org.springaicommunity.mcp.annotation.McpTool;
import org.springaicommunity.mcp.annotation.McpToolParam;
import org.springframework.stereotype.Service;

@Service
public class CalculatorService {

    @McpTool(
            name = "calculate_sum",
            description = "Calculates the sum of two integers. Useful for basic arithmetic."
    )
    public int add(
            @McpToolParam(description = "The first number to add", required = true) int a,
            @McpToolParam(description = "The second number to add", required = true) int b
    ) {
        return a + b;
    }
}
