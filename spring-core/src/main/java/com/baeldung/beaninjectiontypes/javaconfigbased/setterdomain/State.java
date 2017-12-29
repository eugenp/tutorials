package com.baeldung.beaninjectiontypes.javaconfigbased.setterdomain;

public class State {
    private String state;

    public State() {
    }

    public void setState(String type) {
        this.state = type;
    }

    @Override
    public String toString() {
        return String.format("%s", state);
    }
}
