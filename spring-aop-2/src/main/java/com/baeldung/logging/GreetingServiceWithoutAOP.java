package com.baeldung.logging;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

@Service
public class GreetingServiceWithoutAOP {

    private static final Logger logger = LoggerFactory.getLogger(GreetingServiceWithoutAOP.class);

    public String greet(String name) {
        logger.info(">> greet() - {}", name);
        String result = String.format("Hello %s", name);
        logger.info("<< greet() - {}", result);
        return result;
    }
}
