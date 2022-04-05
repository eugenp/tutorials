package com.baeldung.casting;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class Dog extends Animal {
    private static final Logger LOGGER = LoggerFactory.getLogger(Dog.class);

    public void eat() {
        LOGGER.info("dog is eating");
    }
}
