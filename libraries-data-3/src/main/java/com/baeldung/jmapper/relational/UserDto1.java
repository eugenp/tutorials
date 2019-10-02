package com.baeldung.jmapper.relational;


public class UserDto1 {
    
    private long id;
    private String username;
    
    // constructors
    
    public UserDto1() {
        super();
    }

    public UserDto1(long id, String username) {
        super();
        this.id = id;
        this.username = username;
    }

   // getters and setters
    
    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    @Override
    public String toString() {
        return "UserDto [id=" + id + ", username=" + username + "]";
    }
    
}
