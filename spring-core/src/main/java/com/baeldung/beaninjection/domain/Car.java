package com.baeldung.beaninjection.domain;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Optional;

@Component
public class Car {

    private Engine engine;
    private Wheel wheel;
    private Optional<Audio> audio;

    @Autowired
    public Car(Engine engine, Wheel wheel) {
        this.engine = engine;
        this.wheel = wheel;
    }

    @Autowired
    public void setAudio(Optional<Audio> audio) {
        this.audio = audio;
    }

    public void start() {
        audio.ifPresent(a -> a.turnOn());
    }

    public void stop() {
        audio.ifPresent(a -> a.turnOff());
    }

}
