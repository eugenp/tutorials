package com.baeldung.scope.prototype;

import org.slf4j.LoggerFactory;
import org.slf4j.Logger;

public class PrototypeBean {

    private final Logger logger = LoggerFactory.getLogger(this.getClass());

    public PrototypeBean() {
        logger.info("Prototype instance created");
    }
    
    private String name;
    
    public PrototypeBean(String name) {
        this.name = name;
        logger.info("Prototype instance " + name + " created");
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
    
}
