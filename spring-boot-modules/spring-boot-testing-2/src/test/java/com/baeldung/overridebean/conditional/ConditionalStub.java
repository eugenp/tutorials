package com.baeldung.overridebean.conditional;

import com.baeldung.overridebean.Service;

public class ConditionalStub implements Service {

    public String helloWorld() {
        return "hello conditional stub";
    }
}
