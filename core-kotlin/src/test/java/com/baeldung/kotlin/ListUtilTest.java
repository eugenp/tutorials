package com.baeldung.kotlin;

import org.junit.Assert;
import org.junit.Test;

import java.util.Arrays;
import java.util.List;

import static com.baeldung.kotlin.Lists.*;

public class ListUtilTest {

    @Test
    public void shouldSwapTwoElementsInList() {
        List<Integer> list = Arrays.asList(0, 1, 2);

        List<Integer> swappedElements = swap(list, 1, 2);

        Assert.assertEquals(swappedElements, Arrays.asList(0, 2, 1));
    }

}
