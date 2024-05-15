package com.baeldung.shallowcopyvsdeepcopy;

public class Publisher {
    private String name;

    public Publisher(String name){
        this.name = name;
    }

    public Publisher(Publisher original){
        this.name = original.getName();
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
