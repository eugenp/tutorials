package com.baeldung.main;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Arrays;

public class Exec {

    private static final Logger LOGGER = LoggerFactory.getLogger(Exec.class);

    public static void main(String[] args) {
        LOGGER.info("Running the main method");
        if (args.length > 0) {
            LOGGER.info("List of arguments: {}", Arrays.toString(args));
        }
    }
}
