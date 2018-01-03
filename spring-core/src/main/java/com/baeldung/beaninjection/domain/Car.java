package com.baeldung.beaninjection.domain;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class Car {

    private Engine engine;
    private Wheel wheel;
    private Audio audio;

    @Autowired
    public Car(Engine engine, Wheel wheel) {
        this.engine = engine;
        this.wheel = wheel;
    }

    @Autowired
    public void setAudio(Audio audio) {
        this.audio = audio;
    }

    public void start() {
        audio.turnOn();
    }

    public void stop() {
        audio.turnOff();
    }

}
