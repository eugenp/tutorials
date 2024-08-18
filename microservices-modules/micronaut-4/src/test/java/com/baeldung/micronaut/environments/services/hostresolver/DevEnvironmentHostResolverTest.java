package com.baeldung.micronaut.environments.services.hostresolver;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.Test;

import com.baeldung.micronaut.environments.services.HostResolver;

import io.micronaut.context.ApplicationContext;
import io.micronaut.test.extensions.junit5.annotation.MicronautTest;
import jakarta.inject.Inject;

@MicronautTest(environments = { "dev" })
public class DevEnvironmentHostResolverTest {

    @Inject
    HostResolver hostResolver;
    @Inject
    ApplicationContext applicationContext;

    @Test
    public void assertEnvironmentSet() {
        assertThat(applicationContext.getEnvironment()
            .getActiveNames()).containsExactlyInAnyOrder("test", "dev");
    }

    @Test
    public void getHost_whenEnvIsDev_returnsLocalhost() {
        String devHost = hostResolver.getHost();

        assertThat(devHost).isEqualTo("localhost");
    }
}
