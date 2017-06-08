package com.baeldung.ditypes;

import lombok.Data;

@Data
public class Guest {

    private String name;
    
    public Guest(final String name){
        this.name = name;
    }    
    
    // standard setters and getters

    @Override
    public String toString() {
        return String.format("Guest: %s", this.name);
    }    
}
