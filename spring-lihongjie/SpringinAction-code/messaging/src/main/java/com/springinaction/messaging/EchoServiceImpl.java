package com.springinaction.messaging;

public class EchoServiceImpl implements EchoService {

  @Override
  public void echo(String message) {
    System.out.println("ECHO:  " + message);
  }

}
