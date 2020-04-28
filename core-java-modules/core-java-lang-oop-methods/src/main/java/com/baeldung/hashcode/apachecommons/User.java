package com.baeldung.hashcode.apachecommons;

import org.apache.commons.lang.builder.HashCodeBuilder;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

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

    @Override
    public boolean equals(Object o) {
        if (this == o)
            return true;
        if (o == null)
            return false;
        if (this.getClass() != o.getClass())
            return false;
        User user = (User) o;
        return id == user.id && (name.equals(user.name) && email.equals(user.email));
    }

    @Override
    public int hashCode() {
        return new HashCodeBuilder(17, 37).
          append(id).
          append(name).
          append(email).
          toHashCode();
    }
    // getters and setters here
    
}
