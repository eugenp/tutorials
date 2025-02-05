package com.baeldung.exception.indexoutofbounds;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.Arrays;
import java.util.List;

import org.junit.jupiter.api.Test;

class CopyListUsingAddAllMethodDemoUnitTest {

    @Test
    void whenPassValidArrayList_thenCopyListUsingAddAllMethod() {
        List<Integer> source = Arrays.asList(11, 22, 33);

        assertEquals(source, CopyListUsingAddAllMethodDemo.copyList(source));
    }
}