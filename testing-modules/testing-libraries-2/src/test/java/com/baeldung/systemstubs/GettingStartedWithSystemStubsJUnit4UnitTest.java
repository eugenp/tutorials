package com.baeldung.systemstubs;

import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.experimental.runners.Enclosed;
import org.junit.runner.RunWith;
import uk.org.webcompere.systemstubs.rules.EnvironmentVariablesRule;

import static org.assertj.core.api.Assertions.assertThat;

@RunWith(Enclosed.class)
public class GettingStartedWithSystemStubsJUnit4UnitTest {
    public static class SetEnvironmentInsideTest {
        @Rule
        public EnvironmentVariablesRule environmentVariablesRule = new EnvironmentVariablesRule();

        @Test
        public void givenEnvironmentCanBeModified_whenSetEnvironment_thenItIsSet() {
            environmentVariablesRule.set("ENV", "value1");

            assertThat(System.getenv("ENV")).isEqualTo("value1");
        }
    }

    public static class SetEnvironmentAtConstruction {
        @Rule
        public EnvironmentVariablesRule environmentVariablesRule =
          new EnvironmentVariablesRule("ENV", "value1",
            "ENV2", "value2");


        @Test
        public void givenEnvironmentCanBeModified_whenSetEnvironment_thenItIsSet() {
            assertThat(System.getenv("ENV")).isEqualTo("value1");
            assertThat(System.getenv("ENV2")).isEqualTo("value2");
        }
    }

    public static class SetEnvironmentInBefore {
        @Rule
        public EnvironmentVariablesRule environmentVariablesRule =
                new EnvironmentVariablesRule();

        @Before
        public void before() {
            environmentVariablesRule.set("ENV", "value1")
              .set("ENV2", "value2");
        }


        @Test
        public void givenEnvironmentCanBeModified_whenSetEnvironment_thenItIsSet() {
            assertThat(System.getenv("ENV")).isEqualTo("value1");
            assertThat(System.getenv("ENV2")).isEqualTo("value2");
        }
    }
}
