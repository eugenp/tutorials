package com.baeldung.autowire.sample;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * This class presents a field, a constructor, and a setter injection type.
 * Usually, we'd stick with a single approach for a given property. This is just an educational code.
 */
@Component
public class FooService {

    @Autowired
    @FormatterType("Foo")
    private Formatter formatter;

    @Autowired
    public FooService(@FormatterType("Foo") Formatter formatter) {
        this.formatter = formatter;
    }

    @Autowired
    public void setFormatter(@FormatterType("Foo") Formatter formatter) {
        this.formatter = formatter;
    }

    public String doStuff() {
        return formatter.format();
    }

}
