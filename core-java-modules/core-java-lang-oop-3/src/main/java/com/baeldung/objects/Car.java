package com.baeldung.objects;

public class Car {

    private String type;
    private String model;
    private String color;
    private int speed;

    public Car(String type, String model, String color) {
        this.type = type;
        this.model = model;
        this.color = color;
    }

    public String getColor() {
        return color;
    }

    public void setColor(String color) {
        this.color = color;
    }

    public int getSpeed() {
        return speed;
    }
    
    public int increaseSpeed(int increment) {
        if (increment > 0) {
            this.speed += increment;
        } else {
            System.out.println("Increment can't be negative.");
        }
        return this.speed;
    }
    
    public int decreaseSpeed(int decrement) {
        if (decrement > 0 && decrement <= this.speed) {
            this.speed -= decrement;
        } else {
            System.out.println("Decrement can't be negative or greater than current speed.");
        }
        return this.speed;
    }

    @Override
    public String toString() {
        return "Car [type=" + type + ", model=" + model + ", color=" + color + ", speed=" + speed + "]";
    }
    
}
