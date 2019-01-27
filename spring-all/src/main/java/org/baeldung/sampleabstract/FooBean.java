package org.baeldung.sampleabstract;

import org.springframework.stereotype.Component;

@Component
public class FooBean {

    public String value() {

        return "fooBean";
    }
}
