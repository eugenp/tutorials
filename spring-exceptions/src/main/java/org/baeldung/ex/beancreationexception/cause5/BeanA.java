package org.baeldung.ex.beancreationexception.cause5;

import org.springframework.stereotype.Component;

@Component
class BeanA implements IBeanA {

    public BeanA(final String name) {
        super();
        System.out.println(name);
    }

}