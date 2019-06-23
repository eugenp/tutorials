package com.baeldung.string.emptystrings;

class PlainJavaBlankStringCheck {

    boolean isBlankString(String string) {
        return string == null || string.trim().isEmpty();
    }
}
