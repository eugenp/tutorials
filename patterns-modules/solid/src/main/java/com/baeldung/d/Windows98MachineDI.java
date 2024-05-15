package com.baeldung.d;

public class Windows98MachineDI {

  private final Keyboard keyboard;
  private final Monitor monitor;

  public Windows98MachineDI(Keyboard keyboard, Monitor monitor) {
    this.keyboard = keyboard;
    this.monitor = monitor;
  }
}
