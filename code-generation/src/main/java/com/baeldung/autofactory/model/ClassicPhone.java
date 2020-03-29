package com.baeldung.autofactory.model;

import com.google.auto.factory.AutoFactory;
import com.google.auto.factory.Provided;

/**
 * @author aiet
 */
public class ClassicPhone {

    private final String dialpad;
    private final String ringer;
    private String otherParts;

    @AutoFactory
    public ClassicPhone(@Provided String dialpad, @Provided String ringer) {
        this.dialpad = dialpad;
        this.ringer = ringer;
    }

    @AutoFactory
    public ClassicPhone(String otherParts) {
        this("defaultDialPad", "defaultRinger");
        this.otherParts = otherParts;
    }

    public String getDialpad() {
        return dialpad;
    }

    public String getRinger() {
        return ringer;
    }

    public String getOtherParts() {
        return otherParts;
    }
}
