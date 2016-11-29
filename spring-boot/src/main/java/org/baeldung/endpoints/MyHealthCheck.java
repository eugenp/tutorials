package org.baeldung.endpoints;

import org.springframework.boot.actuate.health.Health;
import org.springframework.boot.actuate.health.HealthIndicator;
import org.springframework.stereotype.Component;

@Component
public class MyHealthCheck implements HealthIndicator {

    public Health health() {
        int errorCode = check(); // perform some specific health check
        if (errorCode != 0) {
            return Health.down().withDetail("Error Code", errorCode).withDetail("Description", "You custom MyHealthCheck endpoint is down").build();
        }
        return Health.up().build();
    }

    public int check() {
        // Your logic to check health
        return 1;
    }
}
