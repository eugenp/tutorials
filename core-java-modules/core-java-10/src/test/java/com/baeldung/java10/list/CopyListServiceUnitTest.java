package com.baeldung.java10.list;

import org.junit.Test;

import java.util.Arrays;
import java.util.List;

public class CopyListServiceUnitTest {

    @Test(expected = UnsupportedOperationException.class)
    public void whenModifyCopyOfList_thenThrowsException() {
        List<Integer> copyList = List.copyOf(Arrays.asList(1, 2, 3, 4));
        copyList.add(4);
    }
}
