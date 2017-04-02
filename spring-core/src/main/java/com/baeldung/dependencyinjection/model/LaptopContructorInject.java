package com.baeldung.dependencyinjection.model;

import org.springframework.stereotype.Component;

@Component
public class LaptopContructorInject {

    private Keyboard keyboard;
    private Mouse mouse;

    public LaptopContructorInject(Keyboard keyboard, Mouse mouse) {
        super();
        this.keyboard = keyboard;
        this.mouse = mouse;
    }

    @Override
    public String toString() {
        return "LaptopContructorInject [keyboard=[" + keyboard + "], mouse=[" + mouse + "]]";
    }

    public Keyboard getKeyboard() {
        return keyboard;
    }

    public Mouse getMouse() {
        return mouse;
    }
    
}
