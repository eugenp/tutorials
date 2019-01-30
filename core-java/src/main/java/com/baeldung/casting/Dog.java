package com.baeldung.casting;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * @author zn.wang
 */
public class Dog extends Animal {
    private static final Logger LOGGER = LoggerFactory.getLogger(Dog.class);

    @Override
    public void eat() {
        LOGGER.info("dog is eating");
    }
}
