package com.baeldung.copies;

import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.Arrays;

import static org.junit.jupiter.api.Assertions.*;

public class PersonShallowTest {
    @Test
    public void givenPersonWithHobbies_whenShallowCopyIsMade_thenBothShouldShareHobbiesListAndNewHobbyShouldBeAddedToOriginal() {
        PersonShallow original = new PersonShallow("Alice", 30, new ArrayList<>(Arrays.asList("reading", "swimming")));

        PersonShallow copy = original.shallowCopy();
        copy.getHobbies().add("painting");

         // The copy should share the same memory address for the hobbies list
        assertSame(original.getHobbies(), copy.getHobbies());
         // The new hobby should be added to the original object
        assertTrue(original.getHobbies().contains("painting"));

    }
}