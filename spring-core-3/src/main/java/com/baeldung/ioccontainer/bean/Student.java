package com.baeldung.ioccontainer.bean;

import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;

public class Student {
    static final Logger LOGGER = LogManager.getLogger(Student.class.getName());

    public void postConstruct() {
        LOGGER.info("Student Bean is initialized");
    }
}
