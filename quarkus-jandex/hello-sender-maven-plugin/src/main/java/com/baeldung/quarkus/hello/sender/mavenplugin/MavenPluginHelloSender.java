package com.baeldung.quarkus.hello.sender.mavenplugin;

import com.baeldung.quarkus.hello.service.HelloRetrieving;

import javax.enterprise.context.ApplicationScoped;
import javax.enterprise.event.Observes;

@ApplicationScoped
public class MavenPluginHelloSender {

    public void sendHello(@Observes HelloRetrieving event) {
        event.getHelloReceiver().accept("Hi, I was detected using the Jandex Maven Plugin.");
    }

}
