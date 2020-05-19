package com.baeldung.exitvshalt;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class JvmExitAndHaltDemo {

    private static Logger LOGGER = LoggerFactory.getLogger(JvmExitAndHaltDemo.class);

    static {
        Runtime.getRuntime()
            .addShutdownHook(new Thread(() -> {
                LOGGER.info("Shutdown hook initiated.");
            }));
    }

    public void processAndExit() {
        process();
        LOGGER.info("Calling System.exit().");
        System.exit(0);
    }

    public void processAndHalt() {
        process();
        LOGGER.info("Calling Runtime.getRuntime().halt().");
        Runtime.getRuntime()
            .halt(0);
    }

    private void process() {
        LOGGER.info("Process started.");
    }

}
