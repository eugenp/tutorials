package com.baeldung.noexception;

import com.machinezoo.noexception.ExceptionHandler;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class CustomExceptionHandler extends ExceptionHandler {

    private Logger logger = LoggerFactory.getLogger(CustomExceptionHandler.class);

    @Override
    public boolean handle(Throwable throwable) {

        if (throwable.getClass().isAssignableFrom(RuntimeException.class) || throwable.getClass().isAssignableFrom(Error.class)) {
            return false;
        } else {
            logger.error("Caught Exception ", throwable);
            return true;
        }
    }
}
