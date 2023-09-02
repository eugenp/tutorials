package com.baeldung.shallowvsdeep;

public class Airplane implements Cloneable {

    private final Engine engine;

    private String name;

    private Pilot pilot;

    public Airplane(String name, Engine engine) {
        this.name = name;
        this.engine = engine;
    }

    public Airplane(String name, Engine engine, Pilot pilot) {
        this.name = name;
        this.engine = engine;
        this.pilot = pilot;
    }

    public String getName() {
        return name;
    }

    public Engine getEngine() {
        return engine;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Pilot getPilot() {
        return pilot;
    }

    public void setPilot(Pilot pilot) {
        this.pilot = pilot;
    }

    public Airplane shallowClone() {
        return new Airplane(name, engine, pilot);
    }

    public Airplane deepClone() {
        return new Airplane(name, engine, pilot.deepClone());
    }

    @Override
    public Airplane clone() throws CloneNotSupportedException {
        return (Airplane) super.clone();
    }
}
