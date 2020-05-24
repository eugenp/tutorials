package com.baeldung.architecture.hexagonal.dependencies;

import java.text.SimpleDateFormat;
import java.util.Date;

import com.baeldung.architecture.hexagonal.component.StringTransformationLogger;

public class StringTransformationLoggerToConsoleLoggerAdapter implements StringTransformationLogger {
    private final ConsoleLogger consoleLogger;

    public StringTransformationLoggerToConsoleLoggerAdapter(ConsoleLogger consoleLogger) {
        this.consoleLogger = consoleLogger;
    }

    @Override
    public void logTransformation(Date date, String original, String transformed) {
        consoleLogger.log(String.format("Transformed %s to %s at %s", original, transformed,
          new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(date)));
    }
}
