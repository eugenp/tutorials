package com.baeldung.beaninjection.domain;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class SonyAudio implements Audio {

    private static final Logger LOG = LoggerFactory.getLogger(SonyAudio.class);

    private boolean audioEnabled;

    public SonyAudio(boolean audioEnabled) {
        this.audioEnabled = audioEnabled;
    }

    @Override
    public void turnOn() {
        LOG.debug(audioEnabled ? "Music is played in Sony audio car" : "Audio is disabled");
    }

    @Override
    public void turnOff() {
        LOG.debug(audioEnabled ? "Music has been turned off in Sony audio car" : "Audio is disabled");
    }

}
