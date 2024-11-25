package com.baeldung.emptystrings;

import jakarta.validation.constraints.Pattern;

class SomeClassWithValidations {

    @Pattern(regexp = "\\A(?!\\s*\\Z).+")
    private String someString;

    SomeClassWithValidations setSomeString(String someString) {
        this.someString = someString;
        return this;
    }
}
