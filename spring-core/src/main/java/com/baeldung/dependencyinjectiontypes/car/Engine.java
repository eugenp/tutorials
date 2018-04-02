package com.baeldung.dependencyinjectiontypes.car;

public class Engine {

    private String type;
    private Integer hp;

    public Engine(String type, Integer hp) {
        this.type = type;
        this.hp = hp;
    }

    public String turnOn() {
        return "Motor " + type + " with " + hp + " hp.";
    }
}
