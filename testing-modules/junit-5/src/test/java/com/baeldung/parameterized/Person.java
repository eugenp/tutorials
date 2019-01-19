package com.baeldung.parameterized;

class Person {

    private final String firstName;
    private final String middleName;
    private final String lastName;

    public Person(String firstName, String middleName, String lastName) {
        this.firstName = firstName;
        this.middleName = middleName;
        this.lastName = lastName;
    }

    public String fullName() {
        if (middleName == null || middleName.trim().isEmpty()) return String.format("%s %s", firstName, lastName);

        return String.format("%s %s %s", firstName, middleName, lastName);
    }
}
