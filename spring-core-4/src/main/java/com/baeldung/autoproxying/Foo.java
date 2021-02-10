package com.baeldung.autoproxying;

import org.springframework.stereotype.Component;

@Component
public class Foo {
    public String welcome(String name) {
        return "Hello " + name;
    }
}
