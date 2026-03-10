package com.baeldung.jmod_sample;

import java.util.logging.Logger;

public class Hello {

    private static final Logger LOG = Logger.getLogger(Hello.class.getName());

    public static void main(String[] args) {
        LOG.info("Hello Baeldung!");
    }
}