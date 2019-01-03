package com.baeldung.hexagonal.api;

public interface GreetingApi {

    /**
     * Generic definition of how an Adapter should say hello.
     * @param nameFirst
     * @param nameLast
     * @return String hello greeting, as created at the business logic layer.
     */
    String sayHello(String nameFirst, String nameLast) ;
}
