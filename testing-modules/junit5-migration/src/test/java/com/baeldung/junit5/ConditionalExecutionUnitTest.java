package com.baeldung.junit5;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.condition.*;

import static org.junit.jupiter.api.Assertions.*;

public class ConditionalExecutionUnitTest {

    @Test
    @EnabledOnOs({OS.MAC})
    void whenOperatingSystemIsMac_thenTestIsEnabled() {
        assertEquals(5 + 2, 7);
    }

    @Test
    @DisabledOnOs({OS.WINDOWS})
    void whenOperatingSystemIsWindows_thenTestIsDisabled() {
        assertEquals(5 + 2, 7);
    }

    @Test
    @EnabledOnJre({JRE.JAVA_8})
    void whenRunningTestsOnJRE8_thenTestIsEnabled() {
        assertTrue(5 > 4, "5 is greater the 4");
        assertTrue(null == null, "null is equal to null");
    }

    @Test
    @DisabledOnJre({JRE.JAVA_10})
    void whenRunningTestsOnJRE10_thenTestIsDisabled() {
        assertTrue(5 > 4, "5 is greater the 4");
        assertTrue(null == null, "null is equal to null");
    }

    @Test
    @EnabledIfSystemProperty(named = "os.arch", matches = ".*64.*")
    public void whenRunningTestsOn64BitArchitectures_thenTestIsEnabled() {
        Integer value = 5; // result of an algorithm

        assertNotEquals(0, value, "The result cannot be 0");
    }

    @Test
    @DisabledIfSystemProperty(named = "ci-server", matches = "true")
    public void whenRunningTestsOnCIServer_thenTestIsDisabled() {
        Integer value = 5; // result of an algorithm

        assertNotEquals(0, value, "The result cannot be 0");
    }

    @Test
    @EnabledIfEnvironmentVariable(named = "ENV", matches = "staging-server")
    public void whenRunningTestsStagingServer_thenTestIsEnabled() {
        char[] expected = {'J', 'u', 'p', 'i', 't', 'e', 'r'};
        char[] actual = "Jupiter".toCharArray();

        assertArrayEquals(expected, actual, "Arrays should be equal");
    }

    @Test
    @DisabledIfEnvironmentVariable(named = "ENV", matches = ".*development.*")
    public void whenRunningTestsDevelopmentEnvironment_thenTestIsDisabled() {
        char[] expected = {'J', 'u', 'p', 'i', 't', 'e', 'r'};
        char[] actual = "Jupiter".toCharArray();

        assertArrayEquals(expected, actual, "Arrays should be equal");
    }
}
