/**
 * 
 */
package org.baeldung.testing.abstractclass.indepedentmethod;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

public class AbstractIndependentUnitTest {

    @Test
    public void givenNonAbstractMethod_whenConcreteImpl_testCorrectBehaviour() {
        ConcreteImpl conClass = new ConcreteImpl();
        String actual = conClass.defaultImpl();

        Assertions.assertEquals("DEFAULT-1", actual);
    }

    @Test
    public void givenNonAbstractMethod_whenMockitoMock_testCorrectBehaviour() {
        AbstractIndependent absCls = Mockito.mock(AbstractIndependent.class, Mockito.CALLS_REAL_METHODS);
        Assertions.assertEquals("DEFAULT-1", absCls.defaultImpl());
    }
}
