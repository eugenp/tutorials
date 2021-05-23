package com.baeldung.exceptions.illegalaccesserror;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

public class IllegalAccessErrorSolvedTest {

    @Test()
    public void testNotThrowsIllegalAccessError() {
        Assertions.assertDoesNotThrow(() -> {
            new IllegalAccessErrorSolved().new MySubClassSolved().foobar();
        });
    }
}
