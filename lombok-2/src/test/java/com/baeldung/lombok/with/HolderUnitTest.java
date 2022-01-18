package com.baeldung.lombok.with;


import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotSame;

public class HolderUnitTest {

    @Test
    public void whenWithMethodsGenerated_thenUsable() {
        Holder value = new Holder("a", "b");

        Holder valueModifiedA = value.withVariableA("mod-a");
        Holder valueModifiedB = value.with_variableB("mod-b");
        // Holder valueModifiedC = value.with$VariableC("mod-c"); not possible

        assertNotSame(valueModifiedA, value);
        assertNotSame(valueModifiedB, value);
        assertEquals("mod-a", valueModifiedA.getVariableA());
        assertEquals("mod-b", valueModifiedB.get_variableB());
    }
}
