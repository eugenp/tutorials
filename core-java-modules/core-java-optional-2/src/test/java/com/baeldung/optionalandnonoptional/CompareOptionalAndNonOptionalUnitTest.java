package com.baeldung.optionalandnonoptional;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.Optional;

import org.junit.jupiter.api.Test;

public class CompareOptionalAndNonOptionalUnitTest {
    private static final String A_B_C = "a b c";
    private static final String X_Y_Z = "x y z";

    @Test
    void whenUsingOptionalEquals_thenGetExpectedResult() {
        Optional<String> opt = Optional.empty();
        assertFalse(opt.isPresent() && opt.equals(Optional.of(A_B_C)));

        opt = Optional.of(X_Y_Z);
        assertFalse(opt.isPresent() && opt.equals(Optional.of(A_B_C)));

        opt = Optional.of(A_B_C);
        assertTrue(opt.isPresent() && opt.equals(Optional.of(A_B_C)));
    }

    @Test
    void whenUsingOptionalGet_thenGetExpectedResult() {
        Optional<String> opt = Optional.empty();
        assertFalse(opt.isPresent() && opt.get().equals(A_B_C));

        opt = Optional.of(X_Y_Z);
        assertFalse(opt.isPresent() && opt.get().equals(A_B_C));

        opt = Optional.of(A_B_C);
        assertTrue(opt.isPresent() && opt.get().equals(A_B_C));
    }

    @Test
    void whenUsingOptionalMap_thenGetExpectedResult() {
        Optional<String> opt = Optional.empty();
        assertFalse(opt.map(A_B_C::equals).orElse(false));

        opt = Optional.of(X_Y_Z);
        assertFalse(opt.map(A_B_C::equals).orElse(false));

        opt = Optional.of(A_B_C);
        assertTrue(opt.map(A_B_C::equals).orElse(false));
    }

}