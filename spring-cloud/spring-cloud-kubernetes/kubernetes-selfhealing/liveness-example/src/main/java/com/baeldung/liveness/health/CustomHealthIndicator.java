package com.baeldung.liveness.health;

import org.springframework.boot.actuate.health.Health;
import org.springframework.boot.actuate.health.HealthIndicator;
import org.springframework.stereotype.Component;

import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

@Component
public class CustomHealthIndicator implements HealthIndicator {

    private boolean isHealthy = true;

    public CustomHealthIndicator() {
        ScheduledExecutorService scheduled = Executors.newSingleThreadScheduledExecutor();
        scheduled.schedule(() -> {
            isHealthy = false;
        }, 30, TimeUnit.SECONDS);
    }

    @Override
    public Health health() {
        return isHealthy ? Health.up().build() : Health.down().build();
    }
}
