package com.baeldung.copy;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class CopyUnitTest {

    @Test
    public void givenPerson_whenShallowCopying_thenBothHaveSameHobbies() {
        List<String> hobbies = new ArrayList<>();
        hobbies.add("Reading");
        hobbies.add("Gardening");
        Person originalPerson = new Person("Alice", hobbies);
        Person shallowCopy = new Person(originalPerson.getName(), originalPerson.getHobbies());
        originalPerson.getHobbies().add("Cooking");
        assertEquals(originalPerson.getHobbies(), shallowCopy.getHobbies());
    }

    @Test
    public void givenPerson_whenDeepCopying_thenCopiesHaveDifferentHobbies() {
        List<String> hobbies = new ArrayList<>();
        hobbies.add("Reading");
        hobbies.add("Gardening");
        Person originalPerson = new Person("Alice", hobbies);
        Person deepCopy = new Person(originalPerson.getName(), new ArrayList<>(originalPerson.getHobbies()));
        originalPerson.getHobbies().add("Cooking");
        assertEquals(2, deepCopy.getHobbies().size());
    }
}
