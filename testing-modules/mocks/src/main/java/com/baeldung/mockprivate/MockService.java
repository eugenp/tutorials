package com.baeldung.mockprivate;

public class MockService {

    private final Person person = new Person("John Doe");

    public String getName() {
        return person.getName();
    }
}
