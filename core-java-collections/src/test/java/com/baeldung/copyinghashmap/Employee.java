package com.baeldung.copyinghashmap;

import java.io.Serializable;

public class Employee implements Serializable{
    
    private String firstName;
    private String lastName;
    
    public Employee(String firstName, String lastName) {
        super();
        this.firstName = firstName;
        this.lastName = lastName;
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
    
    @Override
    public String toString() {
        return this.firstName + " " + this.lastName;
    }
 
}


