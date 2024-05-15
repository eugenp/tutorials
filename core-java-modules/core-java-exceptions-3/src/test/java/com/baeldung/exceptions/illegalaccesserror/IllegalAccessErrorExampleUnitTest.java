package com.baeldung.exceptions.illegalaccesserror;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

public class IllegalAccessErrorExampleUnitTest {

    @Test()
    public void givenInterfaceDefaultMethOverriddenPrivateAccess_whenInvoked_thenIllegalAccessError() {
        Assertions.assertThrows(IllegalAccessError.class, () -> {
            new IllegalAccessErrorExample().new MySubClass().foobar();
        });
    }

    @Test()
    public void givenClass1Class2_whenSameClassDefintion_thenNoIllegalAccessError() {
        Assertions.assertDoesNotThrow(() -> {
            new Class2().foo();
        });
    }
}
