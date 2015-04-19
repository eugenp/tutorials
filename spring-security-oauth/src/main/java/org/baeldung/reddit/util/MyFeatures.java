package org.baeldung.reddit.util;

public enum MyFeatures {

    PREDICTION_FEATURE(false);

    private boolean active;

    private MyFeatures(boolean active) {
        this.active = active;
    }

    public boolean isActive() {
        return this.active;
    }

}