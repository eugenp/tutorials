package com.baeldung.autowire.sample;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class FooService {

    @Autowired
    @FormatterType("Foo")
    private Formatter formatter;

    public String doStuff() {
        return formatter.format();
    }

}
