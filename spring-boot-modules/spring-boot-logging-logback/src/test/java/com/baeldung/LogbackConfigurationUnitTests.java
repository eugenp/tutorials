package com.baeldung;

import com.baeldung.logging.LogbackConfiguration;
import com.baeldung.logging.SpringBootLoggingApplication;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import static org.assertj.core.api.Assertions.assertThat;


@RunWith(SpringRunner.class)
@SpringBootTest(classes = SpringBootLoggingApplication.class)
public class LogbackConfigurationUnitTests {

    @Autowired
    private LogbackConfiguration logbackConfiguration;

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
