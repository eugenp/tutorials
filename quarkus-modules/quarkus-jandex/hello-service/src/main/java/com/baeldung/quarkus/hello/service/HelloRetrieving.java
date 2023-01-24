package com.baeldung.quarkus.hello.service;

import java.util.function.Consumer;

public class HelloRetrieving {

    private final Consumer<String> helloReceiver;

    public HelloRetrieving(Consumer<String> helloReceiver) {
        this.helloReceiver = helloReceiver;
    }

    public Consumer<String> getHelloReceiver() {
        return helloReceiver;
    }

}
