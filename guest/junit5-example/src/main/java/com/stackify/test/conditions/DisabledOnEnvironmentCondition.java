package com.stackify.test.conditions;

import java.io.IOException;
import java.util.Arrays;
import java.util.Optional;
import java.util.Properties;

import org.junit.jupiter.api.extension.ConditionEvaluationResult;
import org.junit.jupiter.api.extension.TestExecutionCondition;
import org.junit.jupiter.api.extension.TestExtensionContext;
import org.junit.platform.commons.support.AnnotationSupport;

import com.stackify.utils.ConnectionUtil;

public class DisabledOnEnvironmentCondition implements TestExecutionCondition {

    @Override
    public ConditionEvaluationResult evaluate(TestExtensionContext context) {
        Properties props = new Properties();
        String env = "";
        try {
            props.load(ConnectionUtil.class.getResourceAsStream("/application.properties"));
            env = props.getProperty("env");
        } catch (IOException e) {
            e.printStackTrace();
        }
        Optional<DisabledOnEnvironment> disabled = AnnotationSupport.findAnnotation(context.getElement().get(), DisabledOnEnvironment.class);
        if (disabled.isPresent()) {
            String[] envs = disabled.get().value();
            if (Arrays.asList(envs).contains(env)) {
                return ConditionEvaluationResult.disabled("Disabled on environment " + env);
            }
        }

        return ConditionEvaluationResult.enabled("Enabled on environment "+env);
    }

}
