package com.baeldung.ejb.spring.comparison.ejb.singleton;

import javax.ejb.Singleton;

@Singleton
public class CounterEJB implements CounterEJBRemote {

    private int count = 1;

    public int count() {
        return count++;
    }

}