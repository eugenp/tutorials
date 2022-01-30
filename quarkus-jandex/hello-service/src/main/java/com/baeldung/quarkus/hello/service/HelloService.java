package com.baeldung.quarkus.hello.service;

import javax.enterprise.context.ApplicationScoped;
import javax.enterprise.event.Event;
import javax.inject.Inject;
import java.util.function.Consumer;

@ApplicationScoped
public class HelloService {

    @Inject
    Event<HelloRetrieving> helloRetrievingEvent;

    public void sendHello(Consumer<String> target) {
        helloRetrievingEvent.fire(new HelloRetrieving(target));
    }

}
