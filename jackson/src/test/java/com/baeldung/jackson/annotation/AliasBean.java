package com.baeldung.jackson.annotation;

import com.fasterxml.jackson.annotation.JsonAlias;

public class AliasBean {
    
    @JsonAlias({ "fName", "f_name" })
    private String firstName;
    
    private String lastName;
    
    public AliasBean() {
    
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
