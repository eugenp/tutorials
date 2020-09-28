package com.baeldung;

import org.junit.jupiter.api.Test;

import java.util.Arrays;
import java.util.List;

import static com.baeldung.CopyListUsingJava8StreamDemo.copyList;
import static org.junit.jupiter.api.Assertions.*;

class CopyListUsingJava8StreamDemoUnitTest {

    @Test
    void shouldMakeACopyOfSourceListUsingJava8StreamApi() {
        List<Integer> source = Arrays.asList(11, 22, 33);

        assertEquals(source, copyList(source));
    }
}