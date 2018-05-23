/**
  This class invokes the configuration factory with static initialization,
  as defined in section 4.1 of the "Programmatic Configuration with Log4j 2"
**/
package com.baeldung.logging.log4j2.setconfigurationfactory;

import com.baeldung.logging.log4j2.Log4j2Test;
import com.baeldung.logging.log4j2.simpleconfiguration.CustomConfigurationFactory;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.apache.logging.log4j.Marker;
import org.apache.logging.log4j.MarkerManager;
import org.apache.logging.log4j.core.config.ConfigurationFactory;
import org.junit.BeforeClass;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.JUnit4;

@RunWith(JUnit4.class)
public class SetConfigurationFactoryTest extends Log4j2Test {
    @BeforeClass
    public static void setUp() {
        CustomConfigurationFactory customConfigurationFactory = new CustomConfigurationFactory();
        ConfigurationFactory.setConfigurationFactory(customConfigurationFactory);
    }

    @Test
    public void givenDirectConfiguration_whenUsingFlowMarkers_ThenLogsCorrectly() {
        Logger logger = LogManager.getLogger(this.getClass());
        Marker markerContent = MarkerManager.getMarker("FLOW");
        logger.debug(markerContent, "Debug log message");
        logger.info(markerContent, "Info log message");
        logger.error(markerContent, "Error log message");
    }
}