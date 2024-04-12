package com.baeldung.ejbspringcomparison.ejb.singleton;

import javax.ejb.Remote;

@Remote
public interface CounterEJBRemote {
    int count();
    String getName();
    void setName(String name);
}
