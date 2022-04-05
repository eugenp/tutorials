package com.baeldung.infinispan.repository;

public class HelloWorldRepository {

    public String getHelloWorld() {
        try {
            System.out.println("Executing some heavy query");
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        return "Hello World!";
    }

}
