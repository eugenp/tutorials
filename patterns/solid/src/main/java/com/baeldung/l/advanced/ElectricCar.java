package com.baeldung.l.advanced;

public class ElectricCar extends Car {

  @Override
  protected void turnOnEngine() {
    throw new AssertionError("I am an Electric Car. I don't have an engine!");
  }

  @Override
  protected void accelerate() {
    //this acceleration is crazy!
  }
  
  @Override
  protected void brake() {
      // Apply ElectricCar brake
  }
}
