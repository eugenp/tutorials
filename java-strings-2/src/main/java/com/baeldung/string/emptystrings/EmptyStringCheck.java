package com.baeldung.string.emptystrings;

class EmptyStringCheck {

    boolean isEmptyString(String string) {
        return string == null || string.isEmpty();
    }
}
