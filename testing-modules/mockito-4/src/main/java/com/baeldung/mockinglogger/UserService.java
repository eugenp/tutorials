package com.baeldung.mockinglogger;

import org.slf4j.Logger;

public class UserService {

    private final Logger logger;

    public UserService(Logger logger) {
        this.logger = logger;
    }

    public void checkAdminStatus(boolean isAdmin) {
        if (isAdmin) {
            logger.info("You are an admin, access granted");
        } else {
            logger.error("You are not an admin");
        }
    }

    public void processUser(String username) {
        logger.info("Processing user: {}", username);
        logger.warn("Please don't close your browser ...");
        logger.info("Processing complete");
    }
}
