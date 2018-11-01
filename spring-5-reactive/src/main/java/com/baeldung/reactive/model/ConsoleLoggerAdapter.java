package com.baeldung.reactive.model;

public class ConsoleLoggerAdapter implements Logger {

  @Override
  public void show(Message message) {
    System.out.println("Called ConsoleLogger");
    System.out.println(message.getMessage());
  }
}
