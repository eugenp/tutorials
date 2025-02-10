package com.baeldung;

import io.micronaut.runtime.Micronaut;

import jakarta.inject.Singleton;

@Singleton
public class Application {

    private static boolean running = false;

    public static void main(String[] args) {
        Micronaut.run(Application.class, args);
        running = true;
    }

    public boolean isRunning() {
        return running;
    }
}