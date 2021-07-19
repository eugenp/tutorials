package com.baeldung.jmapper.relational;


public class UserDto2 {
    
    private long id;
    private String email;
    
    // constructors
    
    public UserDto2() {
        super();
    }

    public UserDto2(long id, String email) {
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
        return "UserDto2 [id=" + id + ", email=" + email + "]";
    }
    
}
