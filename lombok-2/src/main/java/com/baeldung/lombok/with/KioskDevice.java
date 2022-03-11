package com.baeldung.lombok.with;

public class KioskDevice extends Device {

    public KioskDevice(String serial, boolean isInspected) {
        super(serial, isInspected);
    }

    @Override
    public Device withInspected(boolean isInspected) {
        return new KioskDevice(getSerial(), isInspected);
    }
}