package com.baeldung.micronaut.environments.services.hostresolver;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.Test;

import com.baeldung.micronaut.environments.services.HostResolver;

import io.micronaut.context.ApplicationContext;
import io.micronaut.test.extensions.junit5.annotation.MicronautTest;
import jakarta.inject.Inject;

@MicronautTest(environments = { "dev" })
public class DevEnvironmentHostResolverUnitTest {

    @Inject
    HostResolver hostResolver;
    @Inject
    ApplicationContext applicationContext;

    @Test
    public void whenEnvironmentIsSetToDev_thenActiveEnvironmentsAreTestAndDev() {
        assertThat(applicationContext.getEnvironment()
            .getActiveNames()).containsExactlyInAnyOrder("test", "dev");
    }

    @Test
    public void givenEnvironmentIsSetToDev_whenGetHost_thenTheResolverReturnsLocalhost() {
        String devHost = hostResolver.getHost();

        assertThat(devHost).isEqualTo("localhost");
    }
}
