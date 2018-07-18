package org.baeldung.testing.abstractclass.abstractmethod;

import static org.junit.Assert.assertEquals;

import org.junit.Test;
import org.mockito.Mockito;

public class AbstractMethodCallingTest {

    @Test
    public void testMockAbstractFuncAndOutputOfDefaultImpl() {

        // mock classes and call real methods available
        AbstractMethodCalling cls = Mockito.mock(AbstractMethodCalling.class);
        Mockito.doReturn("Abstract")
            .when(cls)
            .abstractFunc();
        Mockito.doCallRealMethod()
            .when(cls)
            .defaultImpl();

        // validate result by mock abstractFunc's behaviour
        assertEquals("Abstract Default", cls.defaultImpl());

        // check the value with null response from abstract method
        Mockito.doReturn(null)
            .when(cls)
            .abstractFunc();
        assertEquals("Default", cls.defaultImpl());
    }

}
