package com.baeldung.beaninjection.domain;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class ComputerWithSetterInjection {
    private Mouse mouse;

    @Autowired
    public void setMouse(Mouse mouse) {
        this.mouse = mouse;
    }

    public String toString() {
        return "computerWithSetterInjection with " + mouse;
    }
}
