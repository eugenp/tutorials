package com.baeldung.aspectj;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class SecuredMethod {
    private static final Logger logger = LoggerFactory.getLogger(SecuredMethod.class);

    @Secured(isLocked = true)
    public void lockedMethod() throws Exception {
        logger.info("lockedMethod");
    }

    @Secured(isLocked = false)
    public void unlockedMethod() {
        logger.info("unlockedMethod");
    }

    public static void main(String[] args) throws Exception {
        SecuredMethod sv = new SecuredMethod();
        sv.lockedMethod();
    }
}