package com.baeldung.mediator;

public class Fan {
    private Mediator mediator;
    private boolean isOn = false;

    public void setMediator(Mediator mediator) {
        this.mediator = mediator;
    }

    public boolean isOn() {
        return isOn;
    }

    public void turnOn() {
        this.mediator.start();
        isOn = true;
    }

    public void turnOff() {
        isOn = false;
        this.mediator.stop();
    }
}
