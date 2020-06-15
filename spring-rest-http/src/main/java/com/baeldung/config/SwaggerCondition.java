package com.baeldung.config;

import org.springframework.boot.autoconfigure.condition.ConditionOutcome;
import org.springframework.boot.autoconfigure.condition.SpringBootCondition;
import org.springframework.context.annotation.ConditionContext;
import org.springframework.core.env.Environment;
import org.springframework.core.type.AnnotatedTypeMetadata;

import java.util.Arrays;
import java.util.List;

public class SwaggerCondition extends SpringBootCondition {

    @Override
    public ConditionOutcome getMatchOutcome(ConditionContext context, AnnotatedTypeMetadata metadata) {
        if (matchProfiles(context.getEnvironment())) {
            return ConditionOutcome.match("Swagger ui condition is met.");
        }
        return ConditionOutcome.noMatch("Swagger ui condition is not met");
    }

    private boolean matchProfiles(Environment environment) {
        List<String> profiles = Arrays.asList(environment.getActiveProfiles());
        boolean hasSwagger = profiles.contains("swagger");
        boolean isProd = profiles.contains("prod") ||
                profiles.contains("production") ||
                profiles.contains("live");
        return !isProd && hasSwagger;
    }
}
