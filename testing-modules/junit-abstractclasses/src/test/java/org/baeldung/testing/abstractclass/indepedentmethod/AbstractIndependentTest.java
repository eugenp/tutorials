/**
 * 
 */
package org.baeldung.testing.abstractclass.indepedentmethod;

import static org.junit.Assert.assertEquals;

import org.junit.Test;
import org.mockito.Mockito;

public class AbstractIndependentTest {

    @Test
    public void testUsingConcreteImpl() {
        // create instance
        ConcreteImpl conClass = new ConcreteImpl();
        // call method and capture response
        String actual = conClass.defaultImpl();
        // assert with expected
        assertEquals("DEFAULT-1", actual);
    }

    @Test
    public void testMockitoMock() {
        AbstractIndependent absCls = Mockito.mock(AbstractIndependent.class, Mockito.CALLS_REAL_METHODS);
        assertEquals("DEFAULT-1", absCls.defaultImpl());
    }
}
