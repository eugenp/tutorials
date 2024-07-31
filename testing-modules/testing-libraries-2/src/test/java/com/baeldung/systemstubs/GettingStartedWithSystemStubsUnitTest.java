package com.baeldung.systemstubs;

import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import uk.org.webcompere.systemstubs.environment.EnvironmentVariables;
import uk.org.webcompere.systemstubs.jupiter.SystemStub;
import uk.org.webcompere.systemstubs.jupiter.SystemStubsExtension;
import uk.org.webcompere.systemstubs.properties.SystemProperties;

import static org.assertj.core.api.Assertions.assertThat;
import static uk.org.webcompere.systemstubs.SystemStubs.withEnvironmentVariable;
import static uk.org.webcompere.systemstubs.resource.Resources.with;

class GettingStartedWithSystemStubsUnitTest {
    @Nested
    @ExtendWith(SystemStubsExtension.class)
    class EnvironmentVariablesJUnit5 {
        @SystemStub
        private EnvironmentVariables environmentVariables;

        @Test
        void givenEnvironmentCanBeModified_whenSetEnvironment_thenItIsSet() {
            environmentVariables.set("ENV", "value1");

            assertThat(System.getenv("ENV")).isEqualTo("value1");
        }
    }

    @Nested
    @ExtendWith(SystemStubsExtension.class)
    class EnvironmentVariablesConstructedJUnit5 {
        @SystemStub
        private EnvironmentVariables environmentVariables =
          new EnvironmentVariables("ENV", "value1");

        @Test
        void givenEnvironmentCanBeModified_whenSetEnvironment_thenItIsSet() {
            assertThat(System.getenv("ENV")).isEqualTo("value1");
        }
    }

    @Nested
    @ExtendWith(SystemStubsExtension.class)
    class EnvironmentVariablesConstructedWithSetJUnit5 {
        @SystemStub
        private EnvironmentVariables environmentVariables =
          new EnvironmentVariables()
            .set("ENV", "value1")
            .set("ENV2", "value2");

        @Test
        void givenEnvironmentCanBeModified_whenSetEnvironment_thenItIsSet() {
            assertThat(System.getenv("ENV")).isEqualTo("value1");
        }
    }

    @Nested
    @ExtendWith(SystemStubsExtension.class)
    class EnvironmentVariablesJUnit5ParameterInjection {
        @Test
        void givenEnvironmentCanBeModified_whenSetEnvironment_thenItIsSet(EnvironmentVariables environmentVariables) {
            environmentVariables.set("ENV", "value1");

            assertThat(System.getenv("ENV")).isEqualTo("value1");
        }
    }

    @Nested
    class EnvironmentVariablesExecuteAround {
        @Test
        void givenSetupUsingWithEnvironmentVariable_thenItIsSet() throws Exception {
            withEnvironmentVariable("ENV3", "val")
                .execute(() -> {
                    assertThat(System.getenv("ENV3")).isEqualTo("val");
                });
        }

        @Test
        void givenSetupUsingConstructor_thenItIsSet() throws Exception {
            EnvironmentVariables environment = new EnvironmentVariables()
                    .set("ENV3", "val");
            environment.execute(() -> {
                        assertThat(System.getenv("ENV3")).isEqualTo("val");
                    });
        }

        @Test
        void givenEnvironment_thenCanReturnValue() throws Exception {
            String extracted = new EnvironmentVariables("PROXY", "none")
                    .execute(() -> System.getenv("PROXY"));

            assertThat(extracted).isEqualTo("none");
        }
    }

    @Nested
    class RunMultiple {
        @Test
        void runMultiple() throws Exception {
            with(new EnvironmentVariables("FOO", "bar"),
              new SystemProperties("prop", "val"))
              .execute(() -> {
                  assertThat(System.getenv("FOO")).isEqualTo("bar");
                  assertThat(System.getProperty("prop")).isEqualTo("val");
              });
        }
    }
}
