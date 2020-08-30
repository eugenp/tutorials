package com.baeldung.junit5.templates;

import org.junit.jupiter.api.extension.ConditionEvaluationResult;
import org.junit.jupiter.api.extension.ExecutionCondition;
import org.junit.jupiter.api.extension.ExtensionContext;

import java.io.IOException;
import java.util.Properties;

public class DisabledOnQAEnvironmentExtension implements ExecutionCondition {
    @Override
    public ConditionEvaluationResult evaluateExecutionCondition(ExtensionContext context) {
        Properties properties = new Properties();
        try {
            properties.load(DisabledOnQAEnvironmentExtension.class.getClassLoader()
                .getResourceAsStream("application.properties"));
            if ("qa".equalsIgnoreCase(properties.getProperty("env"))) {
                String reason = String.format("The test '%s' is disabled on QA environment", context.getDisplayName());
                System.out.println(reason);
                return ConditionEvaluationResult.disabled(reason);
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        return ConditionEvaluationResult.enabled("Test enabled");
    }
}