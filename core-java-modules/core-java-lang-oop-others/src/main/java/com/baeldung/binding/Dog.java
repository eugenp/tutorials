package com.baeldung.binding;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Created by madhumita.g on 25-07-2018.
 */
public class Dog extends Animal {

    final static Logger logger = LoggerFactory.getLogger(Dog.class);

    public void makeNoise() {

        logger.info("meow");
    }

}
