package com.baeldung.systemstubs;

import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import uk.org.webcompere.systemstubs.rules.SystemPropertiesRule;

import static org.assertj.core.api.Assertions.assertThat;

public class JUnit4SystemPropertiesUnitTest {
    @Rule
    public SystemPropertiesRule systemProperties =
      new SystemPropertiesRule("db.connection", "false");

    @Before
    public void before() {
        systemProperties.set("before.prop", "before");
    }

    @Test
    public void givenPropertyIsSet_thenCanBeUsedInTest() {
        assertThat(System.getProperty("db.connection")).isEqualTo("false");
    }

    @Test
    public void givenPropertyIsSet_thenAnotherCanBeSetAndBeUsedInTest() {
        assertThat(System.getProperty("db.connection")).isEqualTo("false");

        systemProperties.set("prop2", "true");
        assertThat(System.getProperty("prop2")).isEqualTo("true");
    }

    @Test
    public void givenPropertySetInBefore_thenCanBeSeenInTest() {
        assertThat(System.getProperty("before.prop")).isEqualTo("before");
    }

    @Test
    public void givenPropertySetEarlier_thenNotVisibleLater() {
        assertThat(System.getProperty("prop2")).isNull();
    }
}
