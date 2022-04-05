package com.baeldung.initializationguide;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.Serializable;

public class User implements Serializable, Cloneable {

    private static final Logger LOGGER = LoggerFactory.getLogger(User.class);
    private static final long serialVersionUID = 1L;
    static String forum;
    private String name;
    private int id;

    {
        id = 0;
        LOGGER.debug("Instance Initializer");
    }

    static {
        forum = "Java";
        LOGGER.debug("Static Initializer");
    }

    public User(String name, int id) {
        super();
        this.name = name;
        this.id = id;
    }

    public User() {
        LOGGER.debug("Constructor");
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    @Override
    protected Object clone() throws CloneNotSupportedException {
        return this;
    }

}

