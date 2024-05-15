package com.baeldung.hashcode.lombok;

import lombok.EqualsAndHashCode;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@EqualsAndHashCode
public class User {

    private final Logger logger = LoggerFactory.getLogger(User.class);
    private long id;
    private String name;
    private String email;

    public User(long id, String name, String email) {
        this.id = id;
        this.name = name;
        this.email = email;
    }
    // getters and setters here
    
}
