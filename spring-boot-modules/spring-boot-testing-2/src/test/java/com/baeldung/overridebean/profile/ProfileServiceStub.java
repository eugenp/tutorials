package com.baeldung.overridebean.profile;

import com.baeldung.overridebean.Service;

public class ProfileServiceStub implements Service {

    public String helloWorld() {
        return "hello profile stub";
    }
}
