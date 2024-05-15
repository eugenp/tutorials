package com.baeldung.module2;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class TestB {

    public Logger getLogger() {
        return LoggerFactory.getLogger(TestB.class);
    }
    
    public String getName(String name) {
        return "Hello" + name;
    }

}