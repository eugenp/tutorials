package org.baeldung.testing.abstractclass.instancefields;

import static org.junit.Assert.assertEquals;

import org.junit.Test;
import org.mockito.Mockito;
import org.powermock.api.mockito.PowerMockito;
import org.powermock.reflect.Whitebox;

public class AbstractInstanceFieldsTest {

    @Test
    public void testMethodCountGreaterThan5() {

        // mock
        AbstractInstanceFields instClass = Mockito.mock(AbstractInstanceFields.class);
        Mockito.doCallRealMethod()
            .when(instClass)
            .testFunc();

        // set counter greater than 5
        instClass.count = 7;

        // compare the result
        assertEquals("Overflow", instClass.testFunc());
    }

    @Test
    public void testPrivateFieldActiveSetTrue() {
        AbstractInstanceFields instClass = PowerMockito.mock(AbstractInstanceFields.class);
        PowerMockito.doCallRealMethod()
            .when(instClass)
            .testFunc();
        Whitebox.setInternalState(instClass, "active", true);

        // compare the expected result with actual
        assertEquals("Added", instClass.testFunc());
    }

}
