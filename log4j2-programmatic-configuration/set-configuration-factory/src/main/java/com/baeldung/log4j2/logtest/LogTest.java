package com.baeldung.log4j2.logtest;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.apache.logging.log4j.core.config.ConfigurationFactory;

import com.baeldung.log4j2.config.CustomConfigurationFactory;

public class LogTest {
    static {
        CustomConfigurationFactory customConfigurationFactory = new CustomConfigurationFactory();
        ConfigurationFactory.setConfigurationFactory(customConfigurationFactory);
    }

    private static Logger logger = LogManager.getLogger();

    public static void main(String[] args) {
        logger.debug("Debug log message");
        logger.info("Info log message");
        logger.error("Error log message");
    }
}
