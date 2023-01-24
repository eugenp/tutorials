package com.baeldung.quarkus.hello.sender.undetected;

import com.baeldung.quarkus.hello.service.HelloRetrieving;

import javax.enterprise.context.ApplicationScoped;
import javax.enterprise.event.Observes;

@ApplicationScoped
public class UndetectedHelloSender {

    public void sendHello(@Observes HelloRetrieving event) {
        event.getHelloReceiver().accept("Hi, I do not create a Jandex index, so I should not get detected.");
    }

}
