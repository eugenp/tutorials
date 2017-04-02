package com.baeldung.dependencyinjection.model;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class LaptopSetterInject {

    @Autowired
    private Keyboard keyboard;

    @Autowired
    private Mouse mouse;

    public void setKeyboard(Keyboard keyboard) {
        this.keyboard = keyboard;
    }

    public void setMouse(Mouse mouse) {
        this.mouse = mouse;
    }

    @Override
    public String toString() {
        return "LaptopSetterInject [[" + keyboard + "], [" + mouse + "]]";
    }

}
