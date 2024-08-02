package com.baeldung.checkedcollections;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

public class DataProcessorUnitTest {

    @Test
    void givenGenericCollection_whenInvalidTypeDataAdded_failsAfterInvocation() {
        Collection data = new ArrayList<>();
        data.add("DATA_ONE");
        data.add("DATA_TWO");
        data.add(3); // should have failed here
        DataProcessor dataProcessor = new DataProcessor();
        Assertions.assertThrows(ClassCastException.class, () -> dataProcessor.checkPrefix(data)); // but fails here
    }

    @Test
    void givenGenericCollection_whenInvalidTypeDataAdded_failsAfterAdding() {
        Collection data = Collections.checkedCollection(new ArrayList<>(), String.class);
        Assertions.assertThrows(ClassCastException.class, () -> {
            data.add("DATA_ONE");
            data.add("DATA_TWO");
            data.add(3); // fails here
        });
        DataProcessor dataProcessor = new DataProcessor();
        dataProcessor.checkPrefix(data);
    }
}
