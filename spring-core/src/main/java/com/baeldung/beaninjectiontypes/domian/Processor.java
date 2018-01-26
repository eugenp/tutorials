package com.baeldung.beaninjectiontypes.domian;

public class Processor {

    private int speed;

    private String name;

    public Processor(int speed, String name) {
        super();
        this.speed = speed;
        this.name = name;
    }

    public int getSpeed() {
        return speed;
    }

    public String getName() {
        return name;
    }

    @Override
    public String toString() {
        return "Processor [speed=" + speed + ", name=" + name + "]";
    }

}
