package com.baeldung;

public class Car {

    int number;
    String name;
    Engine engine;

    public static class Engine {

        String model;
        int version;

        public String getModel() {
            return model;
        }

        public int getVersion() {
            return version;
        }

        public Engine(String model, int version) {
            this.model = model;
            this.version = version;
        }

        public Engine() {

        }
    }

    public int getNumber() {
        return number;
    }

    public String getName() {
        return name;
    }

    public Engine getEngine() {
        return engine;
    }

    public Car(int number, String name, Engine engine) {
        this.number = number;
        this.name = name;
        this.engine = engine;
    }

    public Car() {

    }
}
