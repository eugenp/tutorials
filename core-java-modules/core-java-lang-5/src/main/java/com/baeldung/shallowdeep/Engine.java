package com.baeldung.shallowdeep;

public class Engine implements Cloneable {
    private String type;
    private double horsePower;

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public double getHorsePower() {
        return horsePower;
    }

    public void setHorsePower(double horsePower) {
        this.horsePower = horsePower;
    }

    public Engine(String type, double horsePower) {
        super();
        this.type = type;
        this.horsePower = horsePower;
    }

    public Engine(Engine engine) {
        this.type = engine.type;
        this.horsePower = engine.horsePower;
    }

    @Override
    public Object clone() {
        try {
            return (Engine) super.clone();
        } catch (CloneNotSupportedException ex) {
            return new Engine(this.getType(), this.getHorsePower());
        }
    }
}
