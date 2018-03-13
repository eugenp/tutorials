package com.springinaction.messaging;

import org.springframework.context.support.ClassPathXmlApplicationContext;

public class Receiver {
  public static void main(String[] args) {
    new ClassPathXmlApplicationContext("receiver-context.xml");
  }
}
