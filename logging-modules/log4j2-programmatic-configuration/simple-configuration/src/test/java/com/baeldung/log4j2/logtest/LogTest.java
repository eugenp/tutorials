/**
  This class invokes the configuration factory through the run time property,
  as defined in section 4.2 of the "Programmatic Configuration with Log4j 2"
**/
package com.baeldung.log4j2.logtest;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.apache.logging.log4j.Marker;
import org.apache.logging.log4j.MarkerManager;
import org.junit.Test;

public class LogTest {
    @Test
    public void simpleProgrammaticConfiguration() {
        Logger logger = LogManager.getLogger();
        Marker markerContent = MarkerManager.getMarker("FLOW");
        logger.debug(markerContent, "Debug log message");
        logger.info(markerContent, "Info log message");
        logger.error(markerContent, "Error log message");
    }
}
