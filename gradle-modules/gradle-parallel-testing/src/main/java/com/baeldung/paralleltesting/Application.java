package com.baeldung.paralleltesting;

public class Application {

    public static void main(String[] args) {
        System.out.println("Available processors (cores): " + Runtime.getRuntime()
            .availableProcessors());
    }
}