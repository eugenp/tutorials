package com.baeldung.injectiontypes;

public class Teacher {
    
    private String name;
    
    public Teacher( String name ) {
        this.name = name;
    }
    
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
    
}
