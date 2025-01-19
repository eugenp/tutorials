package com.baeldung.exception.indexoutofbounds;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import org.junit.jupiter.api.Test;


class IndexOutOfBoundsExceptionDemoUnitTest {

    @Test
    void givenDestinationArrayListSizeIsZero_whenCopySourceArrayListToDestination_thenShouldThrowIndexOutOfBoundsException() {
        List<Integer> source = Arrays.asList(1, 2, 3, 4, 5);

        assertThrows(IndexOutOfBoundsException.class, () -> IndexOutOfBoundsExceptionDemo.copyList(source));
    }

    @Test
    void givenSourceAndDestinationListSizeIsEqual_whenCopySourceArrayListToDestination_thenShouldNotThrowIndexOutOfBoundsException() {
        List<Integer> source = Collections.emptyList();

        assertEquals(source, IndexOutOfBoundsExceptionDemo.copyList(source));
    }
}
