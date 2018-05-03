package com.baeldung.log4j2.logtest;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.apache.logging.log4j.core.LoggerContext;
import org.apache.logging.log4j.core.config.plugins.util.PluginManager;
import org.junit.Test;


public class LogTest {
    static{
        PluginManager.addPackage("com.baeldung.log4j2.config");
    }
    
    @Test
    public void simpleProgrammaticConfiguration() {
        Logger logger = LogManager.getLogger();
        LoggerContext ctx = (LoggerContext) LogManager.getContext();
        logger.debug("Debug log message");
        logger.info("Info log message");
        logger.error("Error log message");
    }
}
