package com.baeldung.copies;

import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class PersonDeepTest {
    @Test
    void givenPersonWithHobbies_whenCreateDeepCopy_thenCopiedPersonHasSameNameButDifferentHobbiesList() {
        List<String> originalHobbies = new ArrayList<>();
        originalHobbies.add("reading");
        originalHobbies.add("swimming");
        PersonDeep originalPerson = new PersonDeep("John", originalHobbies);

        PersonDeep copiedPerson = new PersonDeep(originalPerson);
        copiedPerson.getHobbies().remove("reading");

        assertFalse(originalPerson.getHobbies().equals(copiedPerson.getHobbies()));
        assertTrue(originalPerson.getHobbies().contains("reading"));
    }
}