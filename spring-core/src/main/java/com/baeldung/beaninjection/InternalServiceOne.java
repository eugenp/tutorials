package com.baeldung.beaninjection;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class InternalServiceOne {

    private final ComponentOne componentOne;
    private final ComponentTwo componentTwo;

    @Autowired
    public InternalServiceOne(ComponentOne componentOne, ComponentTwo componentTwo) {
        this.componentOne = componentOne;
        this.componentTwo = componentTwo;
    }

    public String callComponentOne() {
        return componentOne.componentMethod();
    }

    public String callComponentTwo() {
        return componentTwo.componentMethod();
    }
}
