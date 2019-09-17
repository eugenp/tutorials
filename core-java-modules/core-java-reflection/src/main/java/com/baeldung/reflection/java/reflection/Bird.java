package com.baeldung.java.reflection;

public class Bird extends Animal {
    private boolean walks;

    public Bird() {
        super("bird");
    }

    public Bird(String name, boolean walks) {
        super(name);
        setWalks(walks);
    }

    public Bird(String name) {
        super(name);
    }

    @Override
    public String eats() {
        return "grains";
    }

    @Override
    protected String getSound() {
        return "chaps";
    }

    public boolean walks() {
        return walks;
    }

    public void setWalks(boolean walks) {
        this.walks = walks;
    }
}
