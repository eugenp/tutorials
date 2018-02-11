package com.baeldung.beaninjection;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class InternalServiceTwo {

    private ComponentOne componentOne;
    private ComponentTwo componentTwo;

    @Autowired
    public void setComponentOne(ComponentOne componentOne) {
        this.componentOne = componentOne;
    }

    @Autowired
    public void setComponentTwo(ComponentTwo componentTwo) {
        this.componentTwo = componentTwo;
    }
}
