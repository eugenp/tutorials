package com.baeldung.oop;

public class Car extends Vehicle {

    private String type;
    private String color;
    private int speed;
    private int numberOfGears;

    public Car(String type, String model, String color) {
        super(4, model);
        this.type = type;
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

    public void setSpeed(int speed) {
        this.speed = speed;
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

    public void openDoors() {
        // process to open the doors
    }

    @Override
    public void honk() {
        // produces car-specific honk
    }
}
