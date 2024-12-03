package com.baeldung.envandprop;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Map;

import org.junit.jupiter.api.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class SystemPropertyAndEnvUnitTest {

    private static final Logger log = LoggerFactory.getLogger(SystemPropertyAndEnvUnitTest.class);

    @Test
    void whenUsingSystemGetProperty_thenGetExpectedOutput() {
        String osArch = System.getProperty("os.arch");
        String osName = System.getProperty("os.name");
        String osVersion = System.getProperty("os.version");
        String fileSep = System.getProperty("file.separator");

        log.info("Operating System name: {}", osName);
        log.info("Operating System arch: {}", osArch);
        log.info("Operation System version: {}", osVersion);
        log.info("file separator: {}", fileSep);

        //print all available properties
        System.getProperties()
            .forEach((k, v) -> log.info("{} -> {}", k, v));
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
        log.info("User Home: {}", homeDir);
        log.info("Shell: {}", shell);
        log.info("Terminal: {}", terminal);

        //print all available env variables
        System.getenv()
            .forEach((k, v) -> log.info("{} -> {}", k, v));
    }

    @Test
    void whenAddingEntryToSystemGetEnvMap_thenRaiseException() {
        Map<String, String> sysEnv = System.getenv();
        assertThrows(UnsupportedOperationException.class, () -> sysEnv.put("TECH_SITE", "Baeldung"));
    }

    @Test
    void whenSetEnvVarUsingProcessBuilder_thenVarIsAvailableInChildProcessButNotInCurrent() throws IOException {
        ProcessBuilder pb = new ProcessBuilder("/bin/sh", "-c", "echo $TECH_SITE");
        Map<String, String> env = pb.environment();
        env.put("TECH_SITE", "Baeldung");
        try (BufferedReader output = new BufferedReader(new InputStreamReader(pb.start().getInputStream()))) {
            String result = output.readLine();
            log.info("TECH_SITE in the new process: {}", result);
        }
        log.info("TECH_SITE in the current process: {}", System.getenv("TECH_SITE"));
    }

}