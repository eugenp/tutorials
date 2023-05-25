package com.baeldung.actuator;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.mockito.junit.MockitoJUnitRunner;
import org.springframework.boot.actuate.health.Health;
import org.springframework.boot.actuate.health.Status;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class HealthCheckUnitTest {

    @Test
    public void whenCheckMethodReturnsZero_thenHealthMethodReturnsStatusUP() {
        HealthCheck healthCheck = Mockito.spy(new HealthCheck());
        when(healthCheck.check()).thenReturn(0);
        Health health = healthCheck.health();

        assertThat(health.getStatus(), is(Status.UP));
    }

    @Test
    public void whenCheckMethodReturnsOtherThanZero_thenHealthMethodReturnsStatusDOWN() {
        HealthCheck healthCheck = Mockito.spy(new HealthCheck());
        when(healthCheck.check()).thenReturn(-1);
        Health health = healthCheck.health();

        assertThat(health.getStatus(), is(Status.DOWN));
    }

}
