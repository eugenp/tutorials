package com.baeldung.ejb.tutorial;

import javax.ejb.Stateless;

@Stateless(name = "HelloWorld")
public class HelloWorldBean implements HelloWorld {

    @Override
    public String getHelloWorld() {
        return "Welcome to EJB Tutorial!";
    }

}
