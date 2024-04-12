package com.baeldung.inheritance;

public class SpaceCar extends Car implements SpaceTraveller {
	@Override
    public void floatOnWater() {
        System.out.println("SpaceCar floating!");
    }
    
	@Override
    public void fly() {
        System.out.println("SpaceCar flying!");
    }
    
	@Override
    public void remoteControl() {
        System.out.println("SpaceCar being controlled remotely!");
    }
}
