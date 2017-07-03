package org.baeldung.ex.beancreationexception.cause3;

import org.springframework.stereotype.Component;

@Component
class BeanA {

    public BeanA() {
        super();
        throw new NullPointerException();
    }
}