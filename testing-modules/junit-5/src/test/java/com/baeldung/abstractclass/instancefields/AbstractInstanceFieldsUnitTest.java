package com.baeldung.abstractclass.instancefields;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.powermock.api.mockito.PowerMockito;
import org.powermock.reflect.Whitebox;

public class AbstractInstanceFieldsUnitTest {

    @Test
    public void givenProtectedInstanceField_whenMockClassCountGt5_thenTestNonAbstractMethod() {

        // mock
        AbstractInstanceFields instClass = Mockito.mock(AbstractInstanceFields.class);
        Mockito
          .doCallRealMethod()
          .when(instClass)
          .testFunc();

        // set counter greater than 5
        instClass.count = 7;

        // compare the result
        Assertions.assertEquals("Overflow", instClass.testFunc());
    }

    @Test
    public void givenNonAbstractMethodAndPrivateField_whenPowerMockitoAndActiveFieldTrue_thenCorrectBehaviour() {

        AbstractInstanceFields instClass = PowerMockito.mock(AbstractInstanceFields.class);
        PowerMockito
          .doCallRealMethod()
          .when(instClass)
          .testFunc();
        Whitebox.setInternalState(instClass, "active", true);

        // compare the expected result with actual
        Assertions.assertEquals("Added", instClass.testFunc());
    }

}
