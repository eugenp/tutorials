package com.baeldung;

public class Car {

    int number;
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

    public Engine getEngine() {
        return engine;
    }

    public Car(int number, Engine engine) {
        this.number = number;
        this.engine = engine;
    }

    public Car() {

    }
}
