package com.baeldung.osgi.sample.service.implementation;

import com.baeldung.osgi.sample.service.definition.Greeter;

public class GreeterImpl implements Greeter {

    @Override
    public String sayHiTo(String name) {
        return "Hello " + name;
    }

}
