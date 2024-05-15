package com.baeldung.health;

import org.springframework.boot.actuate.health.Health;
import org.springframework.boot.actuate.health.HealthIndicator;
import org.springframework.stereotype.Component;

@Component
public class WarningHealthIndicator implements HealthIndicator {

    @Override
    public Health health() {
        return Health.status("WARNING").build();
    }
}
