package org.baeldung.web;

import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

@Component
@Transactional
public class BeanA {

    public BeanA() {
        super();
    }

}
