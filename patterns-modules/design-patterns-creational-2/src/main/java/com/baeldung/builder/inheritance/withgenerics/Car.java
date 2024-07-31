package com.baeldung.builder.inheritance.withgenerics;

public class Car extends Vehicle {

    private String make;
    private String model;

    public String getModel() {
        return model;
    }

    public Car(Builder builder) {
        super(builder);
        this.make = builder.make;
        this.model = builder.model;
    }

    public String getMake() {
        return make;
    }

    public static class Builder<T extends Builder<T>> extends Vehicle.Builder<T> {

        protected String make;
        protected String model;

        public T make(String make) {
            this.make = make;
            return self();
        }

        public T model(String model) {
            this.model = model;
            return self();
        }

        @Override
        public Car build() {
            return new Car(this);
        }
    }
}
