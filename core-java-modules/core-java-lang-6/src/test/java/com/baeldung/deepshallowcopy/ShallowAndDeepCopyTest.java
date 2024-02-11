package com.baeldung.deepshallowcopy;

import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertLinesMatch;

public class ShallowAndDeepCopyTest {

    @Test
    public void shallowCopy_ModificationsAffectOriginalObject() {
        List<String> hobbies = new ArrayList<>();
        hobbies.add("Reading");
        hobbies.add("Gardening");

        Person john = new Person("John", 25, hobbies);
        Person shallowCopy = new Person(john.getName(), john.getAge(), john.getHobbies());

        shallowCopy.setName("Shallow");
        shallowCopy.getHobbies().add("Coding");

        assertEquals("John", john.getName());
        assertEquals("Shallow", shallowCopy.getName());
        assertLinesMatch(Arrays.asList("Reading", "Gardening", "Coding"), john.getHobbies());
        assertLinesMatch(Arrays.asList("Reading", "Gardening", "Coding"), shallowCopy.getHobbies());
    }

    @Test
    public void deepCopy_ModificationsDoNotAffectOriginalObject() throws CloneNotSupportedException {
        List<String> hobbies = new ArrayList<>();
        hobbies.add("Reading");
        hobbies.add("Gardening");

        Person john = new Person("John", 25, hobbies);
        Person deepCopy = john.clone();

        deepCopy.setName("Deep");
        deepCopy.getHobbies().add("Coding");

        assertEquals("John", john.getName());
        assertEquals("Deep", deepCopy.getName());
        assertLinesMatch(Arrays.asList("Reading", "Gardening"), john.getHobbies());
        assertLinesMatch(Arrays.asList("Reading", "Gardening", "Coding"), deepCopy.getHobbies());
    }
}