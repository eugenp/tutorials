
/**
  This class loads the logging configuration from the xml defined in 
  src/main/resources and uses the same configuration  generated through 
  programmatic configuration as defined in simple-configuration example.
**/

package com.baeldung.logging.log4j2.xmlconfiguration;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.apache.logging.log4j.Marker;
import org.apache.logging.log4j.MarkerManager;
import org.junit.Test;

public class XMLConfigLogTest {

    @Test
    public void simpleProgrammaticConfiguration() {
        Logger logger = LogManager.getLogger();
        Marker markerContent = MarkerManager.getMarker("FLOW");
        logger.debug(markerContent, "Debug log message");
        logger.info(markerContent, "Info log message");
        logger.error(markerContent, "Error log message");
    }
}
