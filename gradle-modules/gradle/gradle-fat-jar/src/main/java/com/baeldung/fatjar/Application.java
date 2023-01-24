package com.baeldung.fatjar;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


public class Application {

    static final Logger logger = LoggerFactory.getLogger(Application.class);

    public static void main(String[] args) {

       logger.info("Hello at Baeldung!");
    }

}