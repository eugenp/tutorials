package org.baeldung.testing.abstractclass.abstractmethod;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

public class AbstractMethodCallingUnitTest {

    private AbstractMethodCalling cls;

    @BeforeEach
    public void setup() {
        cls = Mockito.mock(AbstractMethodCalling.class);
    }

    @Test
    public void givenDefaultImpl_whenMockAbstractFunc_thenExpectedBehaviour() {
        // mock classes and call real methods available
        Mockito.when(cls.abstractFunc())
            .thenReturn("Abstract");
        // Mockito.doReturn("Abstract")
        // .when(cls)
        // .abstractFunc();
        Mockito.doCallRealMethod()
            .when(cls)
            .defaultImpl();

        // validate result by mock abstractFunc's behaviour
        Assertions.assertEquals("Abstract Default", cls.defaultImpl());

        // check the value with null response from abstract method
        Mockito.doReturn(null)
            .when(cls)
            .abstractFunc();
        Assertions.assertEquals("Default", cls.defaultImpl());
    }

}
