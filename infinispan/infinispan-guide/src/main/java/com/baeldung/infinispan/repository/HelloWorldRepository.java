package com.baeldung.infinispan.repository;

import org.springframework.stereotype.Repository;

@Repository
public class HelloWorldRepository {

    public String getHelloWorld() {
        try {
            System.out.println("Executing some heavy query");
            Thread.sleep(5000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        return "Hello World!";
    }

}
