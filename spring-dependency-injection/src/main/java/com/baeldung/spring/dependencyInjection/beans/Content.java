package com.baeldung.spring.dependencyInjection.beans;

public class Content {

    private String hello;

    public Content() {
        hello = " Hello MrBean!";
    }

    public String sayHello() {
        return hello;
    }
}
