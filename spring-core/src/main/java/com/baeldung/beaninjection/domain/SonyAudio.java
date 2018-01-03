package com.baeldung.beaninjection.domain;

public class SonyAudio implements Audio {

    private boolean audioEnabled;

    public SonyAudio(boolean audioEnabled) {
        this.audioEnabled = audioEnabled;
    }

    @Override
    public void turnOn() {
        System.out.println(audioEnabled ? "Music is played in Sony audio car" : "Audio is disabled");
    }

    @Override
    public void turnOff() {
        System.out.println(audioEnabled ? "Music has been turned off in Sony audio car" : "Audio is disabled");
    }

}
