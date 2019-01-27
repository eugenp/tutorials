package com.baeldung.hexagonal.adaptors;

import java.io.IOException;
import java.util.logging.FileHandler;
import java.util.logging.Formatter;
import java.util.logging.Handler;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.logging.SimpleFormatter;

import com.baeldung.hexagonal.ports.ILogMessage;

public class LoggingOperation implements ILogMessage {

    static Logger logger = Logger.getLogger(LoggingOperation.class.getName());

    public boolean logMessage(int Severity, String message) {

        Handler fileHandler = null;
        Formatter simpleFormatter = null;
        try {
            // Creating FileHandler
            fileHandler = new FileHandler("aplication.log");

            // Creating SimpleFormatter
            simpleFormatter = new SimpleFormatter();

            // Assigning handler to logger
            logger.addHandler(fileHandler);

            // Setting Level to ALL
            fileHandler.setLevel(Level.INFO);
            logger.setLevel(Level.INFO);

            // Logging message in XML format i.e default
            logger.info(message);

            // Setting formatter to the handler
            fileHandler.setFormatter(simpleFormatter);
            logger.info(message);

        } catch (IOException exception) {
            logger.log(Level.SEVERE, "Error occur in FileHandler.", exception);
        }

        return Boolean.TRUE;
    }

    public static void main(String[] args) {
        new LoggingOperation().logMessage(3, "test");
    }

}
