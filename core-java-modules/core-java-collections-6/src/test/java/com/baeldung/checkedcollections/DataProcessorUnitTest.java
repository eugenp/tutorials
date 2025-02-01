package com.baeldung.checkedcollections;

import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;

import org.junit.jupiter.api.Test;

class DataProcessorUnitTest {

    @Test
    void givenGenericCollection_whenInvalidTypeDataAdded_thenFailsAfterInvocation() {
        Collection data = new ArrayList<>();
        data.add("DATA_ONE");
        data.add("DATA_TWO");
        data.add(3); // should have failed here

        DataProcessor dataProcessor = new DataProcessor();

        assertThrows(ClassCastException.class, () -> dataProcessor.checkPrefix(data)); // but fails here
    }

    @Test
    void givenGenericCollection_whenInvalidTypeDataAdded_thenFailsAfterAdding() {
        Collection data = Collections.checkedCollection(new ArrayList<>(), String.class);
        data.add("DATA_ONE");
        data.add("DATA_TWO");

        assertThrows(ClassCastException.class, () -> {
            data.add(3); // fails here
        });

        DataProcessor dataProcessor = new DataProcessor();
        final boolean result = dataProcessor.checkPrefix(data);
        assertTrue(result);

    }
}
