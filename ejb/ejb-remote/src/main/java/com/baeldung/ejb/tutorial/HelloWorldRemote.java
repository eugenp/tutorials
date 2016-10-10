package com.baeldung.ejb.tutorial;

import javax.ejb.*;

@Remote
public interface HelloWorldRemote {
    public String getHelloWorld();
}
