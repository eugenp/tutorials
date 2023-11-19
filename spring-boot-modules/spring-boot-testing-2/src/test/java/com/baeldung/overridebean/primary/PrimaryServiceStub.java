package com.baeldung.overridebean.primary;

import com.baeldung.overridebean.Service;

public class PrimaryServiceStub implements Service {

    public String helloWorld() {
        return "hello primary stub";
    }
}
