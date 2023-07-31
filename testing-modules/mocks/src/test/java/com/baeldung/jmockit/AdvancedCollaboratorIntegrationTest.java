package com.baeldung.jmockit;

import mockit.*;
import org.junit.Test;

import java.io.Serializable;
import java.util.Comparator;
import java.util.List;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNull;

public class AdvancedCollaboratorIntegrationTest {

    interface IList<T> extends List<T> {}
    interface IComparator extends Comparator<Integer>, Serializable {}
    static class MultiMock {
        IList<?> get() { return null; }
        IComparator compareTo() { return null; }
    }

    @Tested
    private AdvancedCollaborator mock;
    

    @Test
    public void testToMockUpPrivateMethod() {
        new MockUp<AdvancedCollaborator>() {
            @Mock
            protected String protectedMethod() {
                return "mocked: ";
            }
        };
        String res = mock.methodThatCallsProtectedMethod(1);
        assertEquals("mocked: 1", res);
    }

    @Test
    public void testToMockUpDifficultConstructor() throws Exception {
        new MockUp<AdvancedCollaborator>() {
            @Mock
            public void $init(Invocation invocation, String string) {
                ((AdvancedCollaborator) invocation.getInvokedInstance()).i = 1;
            }
        };
        AdvancedCollaborator coll = new AdvancedCollaborator(null);
        assertEquals(1, coll.i);
    }

    @Test
    public void testToSetPrivateFieldDirectly(@Injectable("10") int privateField) {
        assertEquals(10, privateField);
    }

    @Test
    public void testToGetPrivateFieldDirectly() {
        assertEquals(5, mock.methodThatReturnsThePrivateField());
    }

    @Test
    @SuppressWarnings("unchecked")
    public void testMultipleInterfacesWholeTest(@Mocked MultiMock multiMock) {
        new Expectations() {
            {
                multiMock.get(); result = null;
                multiMock.compareTo(); result = null;
            }
        };
        assertNull(multiMock.get());
        assertNull(multiMock.compareTo());
    }

}
