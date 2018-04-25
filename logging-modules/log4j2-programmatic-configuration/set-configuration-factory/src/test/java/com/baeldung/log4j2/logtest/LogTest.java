/**
  This class invokes the configuration factory with static initialization,
  as defined in section 4.1 of the "Programmatic Configuration with Log4j 2"
**/
package com.baeldung.log4j2.logtest;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.apache.logging.log4j.Marker;
import org.apache.logging.log4j.MarkerManager;
import org.apache.logging.log4j.core.config.ConfigurationFactory;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.JUnit4;

import com.baeldung.log4j2.config.CustomConfigurationFactory;

@RunWith(JUnit4.class)
public class LogTest {
    static {
        CustomConfigurationFactory customConfigurationFactory = new CustomConfigurationFactory();
        ConfigurationFactory.setConfigurationFactory(customConfigurationFactory);
    }

    @Test
    public void simpleProgrammaticConfiguration() {
        Logger logger = LogManager.getLogger();
        Marker markerContent = MarkerManager.getMarker("FLOW");
        logger.debug(markerContent, "Debug log message");
        logger.info(markerContent, "Info log message");
        logger.error(markerContent, "Error log message");
    }
}
