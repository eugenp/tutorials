package com.baeldung.exception.indexoutofbounds;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.Arrays;
import java.util.List;

import org.junit.jupiter.api.Test;

public class CopyListUsingCollectionsCopyMethodDemoUnitTest {
    @Test
    void whenCopyListUsingCollectionsCopy_thenOverrideAllDestinationListValues() {
        List<Integer> source = Arrays.asList(11, 22, 33);
        List<Integer> destination = Arrays.asList(1, 2, 3);

        CopyListUsingCollectionsCopyMethodDemo.copyList(source, destination);

        assertEquals(source, destination);
    }

    @Test
    void whenCopyListUsingCollectionsCopy_thenOverrideInitialDestinationValuesAndOthersShouldBeUnchanged(){
        List<Integer> source = Arrays.asList(11, 22, 33);
        List<Integer> destination = Arrays.asList(1, 2, 3, 4, 5);
        List<Integer> expectedList = Arrays.asList(11, 22, 33, 4, 5);

        CopyListUsingCollectionsCopyMethodDemo.copyList(source, destination);

        assertEquals(expectedList, destination);
    }
}
