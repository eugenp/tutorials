package com.baeldung.log4j2.logtest;

import org.apache.logging.log4j.Level;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.apache.logging.log4j.core.Appender;
import org.apache.logging.log4j.core.Layout;
import org.apache.logging.log4j.core.LoggerContext;
import org.apache.logging.log4j.core.appender.FileAppender;
import org.apache.logging.log4j.core.config.AppenderRef;
import org.apache.logging.log4j.core.config.Configuration;
import org.apache.logging.log4j.core.config.LoggerConfig;
import org.apache.logging.log4j.core.layout.PatternLayout;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.JUnit4;

@RunWith(JUnit4.class)
public class LogTest {
    
    @Test
    public void simpleProgrammaticConfiguration() {
        Logger logger = LogManager.getLogger();
        final LoggerContext ctx = (LoggerContext) LogManager.getContext(false);
        final Configuration config = ctx.getConfiguration();
        final Layout layout = PatternLayout.createDefaultLayout(config);
        final Appender appender = FileAppender.createAppender("target/test.log", "false", "false", "File", "true", "false", "true", "4000", layout, null, "false", null, config);
        appender.start();
        config.addAppender(appender);
        AppenderRef ref = AppenderRef.createAppenderRef("File", null, null);
        AppenderRef[] refs = new AppenderRef[] { ref };
        LoggerConfig loggerConfig = LoggerConfig.createLogger("false", Level.DEBUG, "org.apache.logging.log4j", "true", refs, null, config, null);
        loggerConfig.addAppender(appender, null, null);
        config.addLogger("com", loggerConfig);
        ctx.updateLoggers();
        logger.debug("Debug log message");
        logger.info("Info log message");
        logger.error("Error log message");
        System.out.println("Logs written to file at target/test.log");
    }
}
