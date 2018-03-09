package com.springinaction.messaging;

import org.springframework.context.support.ClassPathXmlApplicationContext;

public class RpcServiceMain {
  public static void main(String[] args) {
    new ClassPathXmlApplicationContext("rpc-service-context.xml");
  }
}
