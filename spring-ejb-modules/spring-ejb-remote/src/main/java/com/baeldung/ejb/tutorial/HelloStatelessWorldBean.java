package com.baeldung.ejb.tutorial;

import javax.ejb.Stateless;

@Stateless(name = "HelloStatelessWorld")
public class HelloStatelessWorldBean implements HelloStatelessWorld {

    public String getHelloWorld() {
        return "Hello Stateless World!";
    }
 
}
