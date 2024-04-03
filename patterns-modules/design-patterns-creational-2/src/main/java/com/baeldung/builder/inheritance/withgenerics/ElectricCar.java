package com.baeldung.builder.inheritance.withgenerics;

public class ElectricCar extends Car {
    private String batteryType;

    public String getBatteryType() {
        return batteryType;
    }

    public ElectricCar(Builder builder) {
        super(builder);
        this.batteryType = builder.batteryType;
    }

    public static class Builder<T extends Builder<T>> extends Car.Builder<T> {
        protected String batteryType;

        public T batteryType(String batteryType) {
            this.batteryType = batteryType;
            return self();
        }

        @Override
        public ElectricCar build() {
            return new ElectricCar(this);
        }
    }
}
