package com.baeldung.binding;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Created by madhumita.g on 25-07-2018.
 */
public class Animal {

    final static Logger logger = LoggerFactory.getLogger(Animal.class);

    public void makeNoise() {
        logger.info("generic animal noise");
    }

    public void makeNoise(Integer repetitions) {
        while(repetitions != 0) {
            logger.info("generic animal noise countdown " + repetitions);
            repetitions -= 1;
        }
    }
}
