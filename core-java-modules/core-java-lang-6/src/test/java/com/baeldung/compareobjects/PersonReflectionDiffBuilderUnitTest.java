package com.baeldung.compareobjects;

import static org.junit.jupiter.api.Assertions.assertFalse;

import java.util.ArrayList;
import java.util.List;

import org.apache.commons.lang3.builder.Diff;
import org.apache.commons.lang3.builder.DiffResult;
import org.junit.jupiter.api.Test;

public class PersonReflectionDiffBuilderUnitTest {
    @Test
    void givenTwoPeopleDifferent_whenComparingWithReflectionDiffBuilder_thenDifferencesFound() {
        List<PhoneNumber> phoneNumbers1 = new ArrayList<>();
        phoneNumbers1.add(new PhoneNumber("home", "123-456-7890"));
        phoneNumbers1.add(new PhoneNumber("work", "987-654-3210"));

        List<PhoneNumber> phoneNumbers2 = new ArrayList<>();
        phoneNumbers2.add(new PhoneNumber("mobile1", "123-456-7890"));
        phoneNumbers2.add(new PhoneNumber("mobile2", "987-654-3210"));

        Address address1 = new Address("123 Main St", "London", "12345");
        Address address2 = new Address("123 Main St", "Paris", "54321");

        Person person1 = new Person("John", "Doe", 30, phoneNumbers1, address1);
        Person person2 = new Person("Jane", "Smith", 28, phoneNumbers2, address2);

        DiffResult<Person> diff = PersonReflectionDiffBuilder.compare(person1, person2);
        for (Diff<?> d : diff.getDiffs()) {
            System.out.println(d.getFieldName() + ": " + d.getLeft() + " != " + d.getRight());
        }

        assertFalse(diff.getDiffs()
            .isEmpty());
    }
}
