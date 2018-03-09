package com.springinaction.messaging;

import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

public class RpcClientMain {
  public static void main(String[] args) {
    ApplicationContext ctx = new ClassPathXmlApplicationContext("rpc-client-context.xml");
    EchoService service = (EchoService) ctx.getBean("echoService");
    
    System.out.println("CALLING...");
    service.echo("HEY THERE! THIS WORKS!");
    System.out.println("DONE");
  }
}
