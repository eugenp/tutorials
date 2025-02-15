
/**
  This class loads the logging configuration from the xml defined in 
  src/main/resources and uses the same configuration  generated through 
  programmatic configuration as defined in simple-configuration example.
**/

package com.baeldung.logging.log4j2.xmlconfiguration;

import com.baeldung.logging.log4j2.Log4j2BaseUnitTest;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.apache.logging.log4j.Marker;
import org.apache.logging.log4j.MarkerManager;
import org.apache.logging.log4j.core.LoggerContext;
import org.apache.logging.log4j.core.config.plugins.util.PluginManager;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;

@Disabled
public class XMLConfigLogUnitTest extends Log4j2BaseUnitTest {

    @BeforeAll
    public static void setUp() {
        PluginManager.addPackage("com.baeldung.logging.log4j2.xmlconfiguration");
    }

    @Test
    public void givenXMLConfigurationPlugin_whenUsingFlowMarkers_ThenLogsCorrectly() throws Exception {
        Logger logger = LogManager.getLogger(this.getClass());
        Marker markerContent = MarkerManager.getMarker("FLOW");
        logger.debug(markerContent, "Debug log message");
        logger.info(markerContent, "Info log message");
        logger.error(markerContent, "Error log message");
    }

    @Test
    public void givenXMLConfigurationPlugin_whenSimpleLog_ThenLogsCorrectly() throws Exception {
        Logger logger = LogManager.getLogger(this.getClass());
        LoggerContext ctx = (LoggerContext) LogManager.getContext();
        logger.debug("Debug log message");
        logger.info("Info log message");
        logger.error("Error log message");
    }
}
