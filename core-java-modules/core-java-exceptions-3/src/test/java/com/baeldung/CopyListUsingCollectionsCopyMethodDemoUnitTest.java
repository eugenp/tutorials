package com.baeldung;

import org.junit.jupiter.api.Test;

import java.util.Arrays;
import java.util.List;

import static com.baeldung.CopyListUsingCollectionsCopyMethodDemo.copyList;
import static org.junit.jupiter.api.Assertions.*;

class CopyListUsingCollectionsCopyMethodDemoUnitTest {

    @Test
    void shouldCopySourceListElementsInDestinationList() {
        List<Integer> source = Arrays.asList(11, 22, 33);
        List<Integer> destination = Arrays.asList(1, 2, 3);

        copyList(source, destination);

        assertEquals(source, destination);
    }

    @Test
    void shouldOverrideSourceListElementsToDestinationList() {
        List<Integer> source = Arrays.asList(11, 22, 33);
        List<Integer> destination = Arrays.asList(1, 2, 3, 4, 5);
        List<Integer> expectedList = Arrays.asList(11, 22, 33, 4, 5);

        copyList(source, destination);

        assertEquals(expectedList, destination);
    }
}