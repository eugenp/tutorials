package com.baeldung.exceptions.illegalaccesserror;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

public class IllegalAccessErrorSolvedUnitTest {

    @Test()
    public void givenInterfaceDefaultMethOverriddenNonPrivateAccess_whenInvoked_thenNoIllegalAccessError() {
        Assertions.assertDoesNotThrow(() -> {
            new IllegalAccessErrorSolved().new MySubClassSolved().foobar();
        });
    }
}
