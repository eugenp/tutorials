package com.baeldung.quarkus.hello.sender.beansxml;

import com.baeldung.quarkus.hello.service.HelloRetrieving;

import javax.enterprise.context.ApplicationScoped;
import javax.enterprise.event.Observes;

@ApplicationScoped
public class BeansXmlHelloSender {

    public void sendHello(@Observes HelloRetrieving event) {
        event.getHelloReceiver().accept("Hi, I was detected using an empty META-INF/beans.xml file.");
    }

}
