package com.baeldung.logging.slf4j;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.ResourceBundle;
import java.text.MessageFormat;

public class Slf4jLogger implements System.Logger {

    private final String name;
    private final Logger logger;

    public Slf4jLogger(String name) {
        this.name = name;
        logger = LoggerFactory.getLogger(name);
    }

    @Override
    public String getName() {
        return name;
    }

    @Override
    public boolean isLoggable(Level level) {
        switch (level) {
        case OFF:
            return false;
        case TRACE:
            return logger.isTraceEnabled();
        case DEBUG:
            return logger.isDebugEnabled();
        case INFO:
            return logger.isInfoEnabled();
        case WARNING:
            return logger.isWarnEnabled();
        case ERROR:
            return logger.isErrorEnabled();
        case ALL:
        default:
            return true;
        }
    }

    @Override
    public void log(Level level, ResourceBundle bundle, String msg, Throwable thrown) {
        if (!isLoggable(level)) {
            return;
        }

        switch (level) {
        case TRACE:
            logger.trace(msg, thrown);
            break;
        case DEBUG:
            logger.debug(msg, thrown);
            break;
        case INFO:
            logger.info(msg, thrown);
            break;
        case WARNING:
            logger.warn(msg, thrown);
            break;
        case ERROR:
            logger.error(msg, thrown);
            break;
        case ALL:
        default:
            logger.info(msg, thrown);
        }
    }

    @Override
    public void log(Level level, ResourceBundle bundle, String format, Object... params) {
        if (!isLoggable(level)) {
            return;
        }
        String message = MessageFormat.format(format, params);

        switch (level) {
        case TRACE:
            logger.trace(message);
            break;
        case DEBUG:
            logger.debug(message);
            break;
        case INFO:
            logger.info(message);
            break;
        case WARNING:
            logger.warn(message);
            break;
        case ERROR:
            logger.error(message);
            break;
        case ALL:
        default:
            logger.info(message);
        }
    }
}
