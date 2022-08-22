package com.baeldung.toggleboolean;

public class ToggleBoolean {
    public static Boolean toggle(Boolean b) {
        if (b == null) {
            return b;
        }
        return !b;
    }
}