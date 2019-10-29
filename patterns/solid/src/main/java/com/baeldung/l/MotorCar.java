package com.baeldung.l;

public class MotorCar implements Car {

  private Engine engine;

  //Constructors, getters + setters

  public void turnOnEngine() {
    //turn on the engine!
    engine.on();
  }

  public void accelerate() {
    //move forward!
    engine.powerOn(1000);
  }
}
