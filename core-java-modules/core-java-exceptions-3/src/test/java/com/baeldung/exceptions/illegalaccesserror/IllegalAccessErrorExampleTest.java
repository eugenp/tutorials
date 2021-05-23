package com.baeldung.exceptions.illegalaccesserror;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

public class IllegalAccessErrorExampleTest {

    @Test()
    public void testThrowsIllegalAccessError() {
        Assertions.assertThrows(IllegalAccessError.class, () -> {
            new IllegalAccessErrorExample().new MySubClass().foobar();
        });
    }

    @Test()
    public void testNotThrowsIllegalAccessError() {
        Assertions.assertDoesNotThrow(() -> {
            new Class2().foo();
        });
    }
}