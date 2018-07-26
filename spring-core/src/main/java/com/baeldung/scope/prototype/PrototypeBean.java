package com.baeldung.scope.prototype;

import org.apache.log4j.Logger;

public class PrototypeBean {

    private final Logger logger = Logger.getLogger(this.getClass());

    public PrototypeBean() {
        logger.info("Prototype instance created");
    }
}
