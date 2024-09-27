package com.baeldung.envandprop;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import java.util.Map;

import org.junit.jupiter.api.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class SystemPropertyAndEnvUnitTest {

    private static final Logger LOG = LoggerFactory.getLogger(SystemPropertyAndEnvUnitTest.class);

    @Test
    void whenUsingSystemGetProperty_thenGetExpectedOutput() {
        String osArch = System.getProperty("os.arch");
        String osName = System.getProperty("os.name");
        String osVersion = System.getProperty("os.version");
        String fileSep = System.getProperty("file.separator");

        LOG.info("Operating System name: {}", osName);
        LOG.info("Operating System arch: {}", osArch);
        LOG.info("Operation System version: {}", osVersion);
        LOG.info("file separator: {}", fileSep);

        //print all available properties
        System.getProperties()
            .forEach((k, v) -> LOG.info("{} -> {}", k, v));
    }

    @Test
    void whenUsingSystemSetProperty_thenGetExpectedOutput() {
        System.setProperty("nice.tech.site", "Baeldung");
        assertEquals("Baeldung", System.getProperty("nice.tech.site"));
    }

    @Test
    void whenUsingSystemGetEnv_thenGetExpectedOutput() {
        String homeDir = System.getenv("HOME");
        String shell = System.getenv("SHELL");
        String terminal = System.getenv("TERM");
        LOG.info("User Home: {}", homeDir);
        LOG.info("Shell: {}", shell);
        LOG.info("Terminal: {}", terminal);

        //print all available env variables
        System.getenv()
            .forEach((k, v) -> LOG.info("{} -> {}", k, v));
    }

    @Test
    void whenAddingEntryToSystemGetEnvMap_thenRaiseException() {
        Map<String, String> sysEnv = System.getenv();
        assertThrows(UnsupportedOperationException.class, () -> sysEnv.put("TECH_SITE", "Baeldung"));
    }
}