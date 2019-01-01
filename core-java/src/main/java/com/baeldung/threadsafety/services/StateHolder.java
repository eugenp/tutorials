package com.baeldung.threadsafety.services;

public class StateHolder {
    
    private final String state;

    public StateHolder(String state) {
        this.state = state;
    }
    
    public String getState() {
        return state;
    }
}
