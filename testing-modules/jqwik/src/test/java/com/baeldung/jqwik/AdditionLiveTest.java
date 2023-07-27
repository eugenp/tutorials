package com.baeldung.jqwik;

import net.jqwik.api.ForAll;
import net.jqwik.api.Property;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class AdditionLiveTest {
    @Property
    public void additionIsCommutative(@ForAll int a, @ForAll int b) {
        assertEquals(a + b, b + a);
    }
}
