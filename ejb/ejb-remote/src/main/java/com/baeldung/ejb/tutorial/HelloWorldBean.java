package com.baeldung.ejb.tutorial;

import javax.ejb.Stateless;

/**
 * Session Bean implementation class HelloWorldBean
 */
@Stateless
public class HelloWorldBean implements HelloWorldRemote {

    /**
     * Default constructor. 
     */
    public HelloWorldBean() {

    }

    @Override
    public String getHelloWorld() {
        return ("Hello World");
    }

}
