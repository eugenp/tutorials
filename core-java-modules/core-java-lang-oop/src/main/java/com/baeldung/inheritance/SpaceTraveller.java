package com.baeldung.inheritance;

public interface SpaceTraveller extends Floatable, Flyable {
    int duration = 10;
    void remoteControl();
}