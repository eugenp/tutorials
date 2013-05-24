package org.baeldung.web;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

@Component
@Transactional
public class BeanB {

    @Autowired
    private BeanA beanA;

    public BeanB() {
        super();
    }

}
