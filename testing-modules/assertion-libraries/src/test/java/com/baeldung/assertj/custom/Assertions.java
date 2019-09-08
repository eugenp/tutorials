package com.baeldung.assertj.custom;

public class Assertions {
    public static PersonAssert assertThat(Person actual) {
        return new PersonAssert(actual);
    }

    // static factory methods of other assertion classes
}
