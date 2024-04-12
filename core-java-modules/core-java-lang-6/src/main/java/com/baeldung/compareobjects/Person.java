package com.baeldung.compareobjects;

import java.util.List;
import java.util.Objects;

import org.apache.commons.lang3.builder.DiffExclude;

public class Person {
    private String firstName;
    private String lastName;
    private int age;
    private List<PhoneNumber> phoneNumbers;
    @DiffExclude
    private Address address;

    public Person(String firstName, String lastName, int age, List<PhoneNumber> phoneNumbers, Address address) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.age = age;
        this.phoneNumbers = phoneNumbers;
        this.address = address;
    }

    public String getFirstName() {
        return firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public int getAge() {
        return age;
    }

    public List<PhoneNumber> getPhoneNumbers() {
        return phoneNumbers;
    }

    public Address getAddress() {
        return address;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o)
            return true;
        if (o == null || getClass() != o.getClass())
            return false;
        Person person = (Person) o;
        return age == person.age && Objects.equals(firstName, person.firstName) && Objects.equals(lastName, person.lastName) && Objects.equals(phoneNumbers, person.phoneNumbers) && Objects.equals(address, person.address);
    }

    @Override
    public int hashCode() {
        return Objects.hash(firstName, lastName, age, phoneNumbers, address);
    }
}
