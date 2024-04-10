package com.baeldung;

import com.baeldung.logging.LogbackConfiguration;
import org.junit.Test;

import static org.assertj.core.api.Assertions.assertThat;


public class LogbackConfigurationUnitTests {

    private LogbackConfiguration logbackConfiguration = new LogbackConfiguration();

    @Test
    public void givenLogbackConfigurationFile_whenSettingLogbackConfiguration_thenFileLocationSet() {
        // Set the expected logback.xml location
        String expectedLocation = "/test/path/to/logback.xml";
        // Call the method to set the logback configuration file
        logbackConfiguration.setLogbackConfigurationFile(expectedLocation);
        // Verify that the system property is correctly set
        assertThat(System.getProperty("logback.configurationFile")).isEqualTo(expectedLocation);
    }
}
