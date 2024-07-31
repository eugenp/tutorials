package com.baeldung.sample;

import org.springframework.stereotype.Component;

@FormatterType("Foo")
@Component
public class FooFormatter implements Formatter {

    public String format() {
        return "foo";
    }

}
