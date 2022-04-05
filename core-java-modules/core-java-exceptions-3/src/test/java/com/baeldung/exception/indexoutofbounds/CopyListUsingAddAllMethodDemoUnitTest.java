package com.baeldung.exception.indexoutofbounds;

import org.junit.jupiter.api.Test;

import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class CopyListUsingAddAllMethodDemoUnitTest {

    @Test
    void whenPassValidArrayList_thenCopyListUsingAddAllMethod() {
        List<Integer> source = Arrays.asList(11, 22, 33);

        assertEquals(source, CopyListUsingAddAllMethodDemo.copyList(source));
    }
}