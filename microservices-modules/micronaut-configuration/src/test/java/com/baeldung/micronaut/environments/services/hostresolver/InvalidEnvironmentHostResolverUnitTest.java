package com.baeldung.micronaut.environments.services.hostresolver;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

import org.junit.jupiter.api.Test;

import com.baeldung.micronaut.environments.services.HostResolver;

import io.micronaut.context.ApplicationContext;
import io.micronaut.test.extensions.junit5.annotation.MicronautTest;
import jakarta.inject.Inject;

@MicronautTest(environments = { "no-mans-land" })
public class InvalidEnvironmentHostResolverUnitTest {

    @Inject
    HostResolver hostResolver;
    @Inject
    ApplicationContext applicationContext;

    @Test
    public void whenEnvironmentIsSetToIrrelevant_thenActiveEnvironmentsAreTheExpectedOnes() {
        assertThat(applicationContext.getEnvironment()
            .getActiveNames()).containsExactlyInAnyOrder("test", "no-mans-land");
    }

    @Test
    public void givenEnvironmentIsSetToIrrelevant_whenGetHost_throwsUnsupportedEnvironmentException() {
        assertThatThrownBy(() -> hostResolver.getHost()).isInstanceOf(RuntimeException.class)
            .hasMessageContaining("Unsupported environment: ");
    }
}
