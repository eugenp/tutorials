package com.baeldung.scope.prototype;

import org.apache.log4j.Logger;

public class PrototypeBeanWithParam {

    private String name;

    private final Logger logger = Logger.getLogger(this.getClass());

    public PrototypeBeanWithParam() {
        logger.info("Prototype instance with param created");
    }

    public PrototypeBeanWithParam(String name) {
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
