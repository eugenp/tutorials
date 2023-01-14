package com.baeldung;

import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class ShallowCopyObjectTest {

    @Test
    void givenAClonedObject_whenAddingElementsToList_thenBothClasesAreUpdated() {
        // given
        ArrayList<Integer> initialList = new ArrayList<>(List.of(3, 5, 7));
        ShallowCopyObject a = new ShallowCopyObject(initialList);
        ShallowCopyObject b = a.clone();

        // when
        a.getIntegers().add(9);

        // then
        assertEquals(4, a.getIntegers().size());
        assertEquals(4, b.getIntegers().size());
    }
}
