package com.baeldung.jqwik;

import net.jqwik.api.ForAll;
import net.jqwik.api.Property;
import net.jqwik.api.constraints.Positive;

import static org.junit.jupiter.api.Assertions.assertTrue;

public class ShrinkingLiveTest {
    @Property
    public void square(@ForAll @Positive int a) {
        int result = a * a;
        assertTrue(result >= a);
    }
}
