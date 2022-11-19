package com.baeldung.objectcopy;

import org.junit.Test;

import java.util.ArrayList;
import java.util.Arrays;

import static org.junit.jupiter.api.Assertions.*;

public class DeepCopyUnitTest {

    @Test
    public void testDeepCopy() {
        ArrayList<Integer> input = new ArrayList<>(Arrays.asList(3, 4, 5, 6));

        DeepCopy deepCopy = new DeepCopy(input);
        input.add(7);
        assertNotEquals(deepCopy.list, input);

    }

}