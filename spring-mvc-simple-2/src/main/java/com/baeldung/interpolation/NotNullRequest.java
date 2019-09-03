package com.baeldung.interpolation;

import javax.validation.constraints.NotNull;

public class NotNullRequest {

    @NotNull(message = "stringValue has to be present")
    private String stringValue;

    public String getStringValue() {
        return stringValue;
    }

    public void setStringValue(String stringValue) {
        this.stringValue = stringValue;
    }
}
