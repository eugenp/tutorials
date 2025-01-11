package com.baeldung.micronaut.globalexceptionhandler;

import io.micronaut.runtime.Micronaut;

public class ServerApplication {

    public static void main(String[] args) {
        Micronaut.run(ServerApplication.class, args);
    }
}
