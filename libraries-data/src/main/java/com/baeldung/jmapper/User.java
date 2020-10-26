package com.baeldung.jmapper;

import java.time.LocalDate;


public class User {
    
    private long id;    
    private String email;
    private LocalDate birthDate;
    
    // constructors
    
    public User() {
        super();
    }
    
    public User(long id, String email, LocalDate birthDate) {
        super();
        this.id = id;
        this.email = email;
        this.birthDate = birthDate;
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

    public LocalDate getBirthDate() {
        return birthDate;
    }

    public void setBirthDate(LocalDate birthDate) {
        this.birthDate = birthDate;
    }

    @Override
    public String toString() {
        return "User [id=" + id + ", email=" + email + ", birthDate=" + birthDate + "]";
    }
    
}
