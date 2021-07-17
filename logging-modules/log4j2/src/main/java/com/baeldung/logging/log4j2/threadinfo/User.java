package com.baeldung.logging.log4j2.threadinfo;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.Queue;
import java.util.concurrent.ConcurrentLinkedDeque;
import java.util.function.BooleanSupplier;

public class User {
    private static final Logger logger = LogManager.getLogger(User.class);

    private final Queue<BooleanSupplier> actions = new ConcurrentLinkedDeque<>();
    private final String name;

    public User(String name) {
        this.name = name;
    }

    public void disconnect() {
        actions.add(() -> {
            logger.info("Disconnecting");
            return false;
        });
    }

    public void connect() {
        logger.info("Connecting");
    }

    public void throwError(String errorMessage) {
        actions.add(() -> {
            logger.error(new RuntimeException(errorMessage));
            return true;
        });
    }

    public void execute(String action) {
        actions.add(() -> {
            logger.info(action);
            return true;
        });
    }

    public BooleanSupplier actionPoll() {
        return actions.poll();
    }

    public String getName() {
        return name;
    }
}
