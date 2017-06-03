package com.baeldung.ditypes;

import lombok.Data;

@Data
public class Host {
    
    private String name;
    
    public Host(final String name){
        this.name = name;
    }    
    
    // standard setters and getters
    
    @Override
    public String toString() {
        return String.format("Host: %s", this.name);
    }
}
