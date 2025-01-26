package com.baeldung.exception.indexoutofbounds;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.Arrays;
import java.util.List;

import org.junit.jupiter.api.Test;

class CopyListUsingConstructorDemoUnitTest {

    @Test
    void whenCopyListUsingConstructor_thenMakeACopyOfList() {
        List<Integer> source = Arrays.asList(11, 22, 33);

        assertEquals(source, CopyListUsingConstructorDemo.copyList(source));
    }
}
