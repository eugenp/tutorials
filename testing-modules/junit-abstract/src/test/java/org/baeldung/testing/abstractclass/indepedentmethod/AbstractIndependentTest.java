/**
 * 
 */
package org.baeldung.testing.abstractclass.indepedentmethod;

import static org.junit.Assert.assertEquals;

import org.junit.Test;
import org.mockito.Mockito;

public class AbstractIndependentTest {

@Test
public void nonAbstractMethod_concreteImpl_testOutput() {
    ConcreteImpl conClass = new ConcreteImpl();
    String actual = conClass.defaultImpl();

    assertEquals("DEFAULT-1", actual);
}

    @Test
    public void nonAbstractMethod_MockitoMock_testOuput() {
        AbstractIndependent absCls = Mockito.mock(AbstractIndependent.class, Mockito.CALLS_REAL_METHODS);
        assertEquals("DEFAULT-1", absCls.defaultImpl());
    }
}
