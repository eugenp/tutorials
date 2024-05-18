package com.baeldung.jmapper;

import com.googlecode.jmapper.annotations.JGlobalMap;

@JGlobalMap
public class UserDto1 {
    
    private long id;
    private String email;
    
    
    // constructors
    
    public UserDto1() {
        super();
    }

    public UserDto1(long id, String email) {
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
        return "UserDto [id=" + id + ", email=" + email + "]";
    }
    
}
