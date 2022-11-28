package com.baeldung.examples.copying;

public class TestsFixture {

    public static final String JOHN_NAME = "John Doe";
    public static final String JANE_NAME = "Jane Doe";

    public static Person johnDoe() {
        return new Person(JOHN_NAME, 30);
    }

    public static Person janeDoe() {
        return new Person(JANE_NAME, 30);
    }
}
