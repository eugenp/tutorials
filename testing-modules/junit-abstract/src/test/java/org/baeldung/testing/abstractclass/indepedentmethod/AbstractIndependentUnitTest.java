/**
 * 
 */
package org.baeldung.testing.abstractclass.indepedentmethod;

import static org.junit.Assert.assertEquals;

import org.junit.Test;
import org.mockito.Mockito;

public class AbstractIndependentUnitTest {

@Test
public void givenNonAbstractMethod_whenConcreteImpl_testCorrectBehaviour() {
    ConcreteImpl conClass = new ConcreteImpl();
    String actual = conClass.defaultImpl();

    assertEquals("DEFAULT-1", actual);
}

    @Test
    public void givenNonAbstractMethod_whenMockitoMock_testCorrectBehaviour() {
        AbstractIndependent absCls = Mockito.mock(AbstractIndependent.class, Mockito.CALLS_REAL_METHODS);
        assertEquals("DEFAULT-1", absCls.defaultImpl());
    }
}
