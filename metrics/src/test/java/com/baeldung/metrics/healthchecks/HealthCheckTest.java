package com.baeldung.metrics.healthchecks;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.junit.Assert.assertThat;

import java.util.Map;

import org.junit.Test;

import com.codahale.metrics.health.HealthCheck;
import com.codahale.metrics.health.HealthCheckRegistry;

public class HealthCheckTest {
    @Test
    public void testHealthCheck() {
        HealthCheckRegistry healthCheckRegistry = new HealthCheckRegistry();

        healthCheckRegistry.register("db", new DatabaseHealthCheck());
        healthCheckRegistry.register("uc", new UserCenterHealthCheck());

        assertThat(healthCheckRegistry.getNames().size(), equalTo(2));

        Map<String, HealthCheck.Result> results = healthCheckRegistry.runHealthChecks();
        for (Map.Entry<String, HealthCheck.Result> entry : results.entrySet()) {
            assertThat(entry.getValue().isHealthy(), equalTo(true));
        }

        healthCheckRegistry.unregister("uc");
        assertThat(healthCheckRegistry.getNames().size(), equalTo(1));
    }
}