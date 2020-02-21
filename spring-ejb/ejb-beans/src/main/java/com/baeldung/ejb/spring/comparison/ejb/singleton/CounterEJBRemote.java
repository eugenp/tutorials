package com.baeldung.ejb.spring.comparison.ejb.singleton;

import javax.ejb.Remote;

@Remote
public interface CounterEJBRemote {
    int count();
}
