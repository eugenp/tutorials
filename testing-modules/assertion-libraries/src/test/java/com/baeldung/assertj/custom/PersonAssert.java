package com.baeldung.assertj.custom;

import org.assertj.core.api.AbstractAssert;

public class PersonAssert extends AbstractAssert<PersonAssert, Person> {

    public PersonAssert(Person actual) {
        super(actual, PersonAssert.class);
    }

    public static PersonAssert assertThat(Person actual) {
        return new PersonAssert(actual);
    }

    public PersonAssert hasFullName(String fullName) {
        isNotNull();
        if (!actual.getFullName().equals(fullName)) {
            failWithMessage("Expected person to have full name %s but was %s", fullName, actual.getFullName());
        }
        return this;
    }

    public PersonAssert isAdult() {
        isNotNull();
        if (actual.getAge() < 18) {
            failWithMessage("Expected person to be adult");
        }
        return this;
    }

    public PersonAssert hasNickname(String nickName) {
        isNotNull();
        if (!actual.getNicknames().contains(nickName)) {
            failWithMessage("Expected person to have nickname %s", nickName);
        }
        return this;
    }
}
