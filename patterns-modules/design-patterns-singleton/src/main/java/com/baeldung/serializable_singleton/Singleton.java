package com.baeldung.serializable_singleton;

import java.io.Serializable;

public class Singleton implements Serializable {

    private static Singleton INSTANCE;
    private String state = "State Zero";

    private Singleton() {        
    }
    
    public static Singleton getInstance() {
        if(INSTANCE == null) {
            INSTANCE = new Singleton();
        }
        
        return INSTANCE;
    }

    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
    }
}