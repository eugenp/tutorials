package com.baeldung;

import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class DeepCopyObjectTest {

    @Test
    void givenAClonedObjectWithCopyConstructor_whenAddingElementsToList_onlyOneListIsUpdated() {
        // given
        ArrayList<Integer> initialList = new ArrayList<>(List.of(3, 5, 7));
        DeepCopyObject a = new DeepCopyObject(initialList);
        DeepCopyObject b = new DeepCopyObject(a);

        // when
        a.getIntegers().add(9);

        // then
        assertEquals(4, a.getIntegers().size());
        assertEquals(3, b.getIntegers().size());
    }

    @Test
    void givenAClonedObjectWithSerialization_whenAddingElementsToList_onlyOneListIsUpdated() throws IOException, ClassNotFoundException {
        // given
        ArrayList<Integer> initialList = new ArrayList<>(List.of(3, 5, 7));
        DeepCopyObject a = new DeepCopyObject(initialList);
        DeepCopyObject b = a.deepCopy();

        // when
        a.getIntegers().add(9);

        // then
        assertEquals(4, a.getIntegers().size());
        assertEquals(3, b.getIntegers().size());
    }
}
