package com.baeldung.string.emptystrings;

import javax.validation.constraints.Pattern;

class SomeClassWithValidations {

    @Pattern(regexp = "\\A(?!\\s*\\Z).+")
    private String someString;

    SomeClassWithValidations setSomeString(String someString) {
        this.someString = someString;
        return this;
    }
}
