package com.baeldung.exception.indexoutofbounds;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.Arrays;
import java.util.List;

import org.junit.jupiter.api.Test;

class CopyListUsingJava8StreamDemoUnitTest {

    @Test
    void whenCopyListUsingStream_thenMakeACopyOfArrayList() {
        List<Integer> source = Arrays.asList(11, 22, 33);

        assertEquals(source, CopyListUsingJava8StreamDemo.copyList(source));
    }
}
