package com.habuma.spitter.service.rmi;

public class HelloServiceImpl implements HelloService {
  public String sayHello(String name) {
    System.out.println("Hello " + name);
    return "Hello " + name;
  }
}
