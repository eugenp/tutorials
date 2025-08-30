package com.baeldung.logging.log4j2;

import static org.junit.jupiter.api.Assertions.assertTrue;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;

import org.apache.log4j.xml.DOMConfigurator;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.apache.logging.log4j.core.config.Configurator;
import org.junit.jupiter.api.Test;

public class DynamicFileAppenderUnitTest {
    @Test
    public void givenLog4j2DynamicFileNameConfig_whenLogToFile_thenFileIsCreated() throws Exception {
        System.setProperty("log4j.configurationFile", "src/test/resources/log4j2-dynamic.xml");
        System.setProperty("logfilename", "app-dynamic-log");

        Logger logger = LogManager.getLogger(DynamicFileAppenderUnitTest.class);
        String expectedMessage = "This is an ERROR log message to the same file.";
        logger.error(expectedMessage);

        File file = new File("app-dynamic-log.log");
        assertTrue(file.exists(), "Log file should be created dynamically.");

        String content = Files.readString(file.toPath());
        assertTrue(content.contains(expectedMessage), "Log file should contain the logged message.");
    }
}
