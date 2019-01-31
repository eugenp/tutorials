package com.baeldung.d;

public class Windows98Machine {

  private final Keyboard keyboard;
  private final Monitor monitor;

  public Windows98Machine() {

    monitor = new Monitor();
    keyboard = new Keyboard();

  }

}
