package com.baeldung.testing.assertj.custom;

public class Assertions {
    public static PersonAssert assertThat(Person actual) {
        return new PersonAssert(actual);
    }

    public static CarAssert assertThat(Car actual) {
        return new CarAssert(actual);
    }
}
