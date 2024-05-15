package com.baeldung.annotations;

import org.springframework.boot.autoconfigure.condition.ConditionalOnWebApplication;
import org.springframework.context.annotation.Configuration;

@Configuration
public class WebApplicationSpecificConfiguration {

    @ConditionalOnWebApplication
    HealthCheckController healthCheckController() {
        return new HealthCheckController();
    }
}
