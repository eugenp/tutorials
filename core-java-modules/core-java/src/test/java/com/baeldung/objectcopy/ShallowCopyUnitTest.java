package com.baeldung.objectcopy;

import org.junit.Test;

import java.util.ArrayList;
import java.util.Arrays;

import static org.junit.jupiter.api.Assertions.*;

public class ShallowCopyUnitTest {

    @Test
    public void testShallowCopy() {
        ArrayList<Integer> input = new ArrayList<>(Arrays.asList(3, 4, 5, 6));

        ShallowCopy shallowCopy = new ShallowCopy(input);
        input.add(7);
        assertEquals(shallowCopy.list, input);

    }

}