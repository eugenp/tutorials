package com.baeldung.immutableobjects;

import static org.junit.Assert.assertEquals;

import java.util.ArrayList;
import java.util.List;

import org.junit.Test;

public class ImmutableObjectsUnitTest {

    @Test
    public void whenCallingStringReplace_thenStringDoesNotMutate() {
        // 2. What's an Immutable Object?
        final String name = "baeldung";
        final String newName = name.replace("dung", "----");

        assertEquals("baeldung", name);
        assertEquals("bael----", newName);
    }

    public void whenReassignFinalValue_thenCompilerError() {
        // 3. The final Keyword in Java (1)
        final String name = "baeldung";
        // name = "bael...";
    }

    @Test
    public void whenAddingElementToList_thenSizeChange() {
        // 3. The final Keyword in Java (2)
        final List<String> strings = new ArrayList<>();
        assertEquals(0, strings.size());
        strings.add("baeldung");
        assertEquals(1, strings.size());
    }
}
