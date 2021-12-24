/*******************************************************************************
 *
 * Copyright 2017-2021, Bemindt Consulting Group, Inc.
 * 
 * User.java
 * rich
 * Dec 23, 2021
 * 
 *******************************************************************************/

package com.baeldung.hexagonal.dto;

public class User {
    public int id;
    public String firstName;
    public String lastName;

    public int getId() {
        return id;
    }
    
    public void setId(int id) {
        this.id = id;
    }
    
    public String getFirstName() {
        return firstName;
    }
    
    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }
    
    public String getLastName() {
        return lastName;
    }
    
    public void setLastName(String lastName) {
        this.lastName = lastName;
    }
}
