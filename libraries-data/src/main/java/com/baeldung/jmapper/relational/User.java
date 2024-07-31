package com.baeldung.jmapper.relational;

import com.googlecode.jmapper.annotations.JMap;


public class User {
    
    @JMap(classes = {UserDto1.class, UserDto2.class})
    private long id;    
    
    @JMap(attributes = {"username", "email"}, classes = {UserDto1.class, UserDto2.class})
    private String email;
    
    // constructors
    
    public User() {
        super();
    }
    
    public User(long id, String email) {
        super();
        this.id = id;
        this.email = email;
    }

    // getters and setters
    
    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    @Override
    public String toString() {
        return "User [id=" + id + ", email=" + email + "]";
    }
    
}
