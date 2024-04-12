package com.baeldung.toggleboolean;

import static org.assertj.core.api.AssertionsForClassTypes.assertThatThrownBy;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.Test;

class ToggleBooleanUnitTest {
    @Test
    void givenPrimitiveBoolean_whenUsingNotOperator_shouldToggleTheBoolean() {
        boolean b = true;
        b = !b;
        assertFalse(b);

        b = !b;
        assertTrue(b);
    }

    @Test
    void givenNullBoxedBoolean_whenUsingNotOperator_shouldThrowNPE() {
        assertThatThrownBy(() -> {
            Boolean b = null;
            b = !b;
        }).isInstanceOf(NullPointerException.class);
    }

    @Test
    void givenPrimitiveBoolean_whenUsingXorOperator_shouldToggleTheBoolean() {
        boolean b = true;
        b ^= true;
        assertFalse(b);

        b ^= true;
        assertTrue(b);
    }

    @Test
    void givenBooleanObj_whenToggle_shouldGetExpectedResult() {
        //boxed Boolean
        Boolean b = true;
        b = ToggleBoolean.toggle(b);
        assertFalse(b);

        b = ToggleBoolean.toggle(b);
        assertTrue(b);

        b = null;
        b = ToggleBoolean.toggle(b);
        assertNull(b);

        //primitive boolean
        boolean bb = true;
        bb = ToggleBoolean.toggle(bb);
        assertFalse(bb);
        bb = ToggleBoolean.toggle(bb);
        assertTrue(bb);
    }
}
