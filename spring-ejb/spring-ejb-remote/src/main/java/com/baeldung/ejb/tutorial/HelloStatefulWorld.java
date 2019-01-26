package com.baeldung.ejb.tutorial;

import javax.ejb.Remote;

@Remote
public interface HelloStatefulWorld {

    int howManyTimes();
    String getHelloWorld();
    
}
