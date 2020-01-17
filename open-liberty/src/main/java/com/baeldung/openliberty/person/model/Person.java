package com.baeldung.openliberty.person.model;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;

@Entity
public class Person {
    
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Id
    private int id;
    
    @NotBlank
    private String username;
   
    @Email
    private String email;
    
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getUsername() {
        return username;
    }
    
    public void setUsername(String username) {
        this.username = username;
    }
    
    public String getEmail() {
        return email;
    }
    
    public void setEmail(String email) {
        this.email = email;
    }
    
    public Person(int id, @NotBlank String username, @Email String email) {
        super();
        this.id = id;
        this.username = username;
        this.email = email;
    }
    
    public Person() {
        super();
    }

    public String toString() {
        return this.id + ":" +this.username;
    }
}
