package com.baeldung.mcp.mcpserveroauth2;

import org.springframework.ai.tool.annotation.Tool;
import org.springframework.ai.tool.annotation.ToolParam;
import org.springframework.stereotype.Service;

import com.baeldung.mcp.mcpserveroauth2.model.CalculationResult;

@Service
public class CalculatorService {

    @Tool(description = "Add two numbers")
    public CalculationResult add(@ToolParam(description = "First number") double a, @ToolParam(description = "Second number") double b) {

        double result = a + b;
        return new CalculationResult("addition", a, b, result);
    }

    @Tool(description = "Subtract two numbers")
    public CalculationResult subtract(@ToolParam(description = "First number") double a, @ToolParam(description = "Second number") double b) {

        double result = a - b;
        return new CalculationResult("subtraction", a, b, result);
    }

    @Tool(description = "Multiply two numbers")
    public CalculationResult multiply(@ToolParam(description = "First number") double a, @ToolParam(description = "Second number") double b) {

        double result = a * b;
        return new CalculationResult("multiplication", a, b, result);
    }

    @Tool(description = "Divide two numbers")
    public CalculationResult divide(@ToolParam(description = "First number") double a, @ToolParam(description = "Second number") double b) {

        if (b == 0) {
            throw new IllegalArgumentException("Cannot divide by zero");
        }

        double result = a / b;
        return new CalculationResult("division", a, b, result);
    }
}