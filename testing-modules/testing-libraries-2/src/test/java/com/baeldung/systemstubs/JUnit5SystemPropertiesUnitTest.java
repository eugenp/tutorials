package com.baeldung.systemstubs;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import uk.org.webcompere.systemstubs.jupiter.SystemStub;
import uk.org.webcompere.systemstubs.jupiter.SystemStubsExtension;
import uk.org.webcompere.systemstubs.properties.SystemProperties;
import uk.org.webcompere.systemstubs.resource.PropertySource;

import static org.assertj.core.api.Assertions.assertThat;

class JUnit5SystemPropertiesUnitTest {

    @ExtendWith(SystemStubsExtension.class)
    @Nested
    class RestoreSystemProperties {
        @SystemStub
        private SystemProperties systemProperties;

        @Test
        void givenAPropertyIsSet_thenItIsOnlyAvailableInsideThisTest1() {
            assertThat(System.getProperty("localProperty")).isNull();

            System.setProperty("localProperty", "nonnull");
            assertThat(System.getProperty("localProperty")).isEqualTo("nonnull");
        }

        @Test
        void givenAPropertyIsSet_thenItIsOnlyAvailableInsideThisTest2() {
            assertThat(System.getProperty("localProperty")).isNull();

            System.setProperty("localProperty", "true");
            assertThat(System.getProperty("localProperty")).isEqualTo("true");
        }
    }

    @ExtendWith(SystemStubsExtension.class)
    @Nested
    class RestoreSystemPropertiesByParameter {

        @Test
        void givenAPropertyIsSet_thenItIsOnlyAvailableInsideThisTest1(SystemProperties systemProperties) {
            assertThat(System.getProperty("localProperty")).isNull();

            System.setProperty("localProperty", "nonnull");
            assertThat(System.getProperty("localProperty")).isEqualTo("nonnull");
        }

        @Test
        void givenAPropertyIsSet_thenItIsOnlyAvailableInsideThisTest2(SystemProperties systemProperties) {
            assertThat(System.getProperty("localProperty")).isNull();

            System.setProperty("localProperty", "true");
            assertThat(System.getProperty("localProperty")).isEqualTo("true");
        }
    }

    @ExtendWith(SystemStubsExtension.class)
    @Nested
    class SetSomeSystemProperties {
        @SystemStub
        private SystemProperties systemProperties;

        @BeforeEach
        void before() {
            systemProperties.set("beforeProperty", "before");
        }

        @Test
        void givenAPropertyIsSetInBefore_thenItIsAvailableInsideThisTest() {
            assertThat(System.getProperty("beforeProperty")).isEqualTo("before");
        }
    }

    @ExtendWith(SystemStubsExtension.class)
    @Nested
    class SetSomeSystemPropertiesFromResources {
        @SystemStub
        private SystemProperties systemProperties =
                new SystemProperties(PropertySource.fromResource("test.properties"));

        @Test
        void givenPropertiesReadFromResources_thenCanBeUsed() {
            assertThat(System.getProperty("name")).isEqualTo("baeldung");
        }
    }
}
