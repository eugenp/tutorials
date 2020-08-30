package com.baeldung.multireleaseapp;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class App {

    private static final Logger logger = LoggerFactory.getLogger(App.class);

    public static void main(String[] args) {
        logger.info(String.format("Running on %s", new DefaultVersion().version()));
    }

}
