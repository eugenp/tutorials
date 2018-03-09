package com.habuma.spitter.service.rmi;

import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

public class RmiMain {
  public static void main(String[] args) {
    ApplicationContext context = new ClassPathXmlApplicationContext("remote-service-context-2.xml");
    
  }
}
