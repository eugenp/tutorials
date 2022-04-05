package com.baeldung.jmockit;

import static org.junit.Assert.assertEquals;

import java.util.ArrayList;
import java.util.List;

import com.baeldung.jmockit.AdvancedCollaborator;
import org.junit.Test;
import org.junit.runner.RunWith;

import mockit.Deencapsulation;
import mockit.Expectations;
import mockit.Invocation;
import mockit.Mock;
import mockit.MockUp;
import mockit.Mocked;
import mockit.Tested;
import mockit.integration.junit4.JMockit;

@RunWith(JMockit.class)
public class AdvancedCollaboratorIntegrationTest<MultiMock extends List<String> & Comparable<List<String>>> {

    @Tested
    private AdvancedCollaborator mock;
    
    @Mocked
    private MultiMock multiMock;

    @Test
    public void testToMockUpPrivateMethod() {
        new MockUp<AdvancedCollaborator>() {
            @Mock
            private String privateMethod() {
                return "mocked: ";
            }
        };
        String res = mock.methodThatCallsPrivateMethod(1);
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
    public void testToSetPrivateFieldDirectly() {
        Deencapsulation.setField(mock, "privateField", 10);
        assertEquals(10, mock.methodThatReturnsThePrivateField());
    }

    @Test
    public void testToGetPrivateFieldDirectly() {
        int value = Deencapsulation.getField(mock, "privateField");
        assertEquals(5, value);
    }

    @Test
    @SuppressWarnings("unchecked")
    public void testMultipleInterfacesWholeTest() {
        new Expectations() {
            {
                multiMock.get(5); result = "foo";
                multiMock.compareTo((List<String>) any); result = 0;
            }
        };
        assertEquals("foo", multiMock.get(5));
        assertEquals(0, multiMock.compareTo(new ArrayList<>()));
    }

    @Test
    @SuppressWarnings("unchecked")
    public <M extends List<String> & Comparable<List<String>>> void testMultipleInterfacesOneMethod(@Mocked M mock) {
        new Expectations() {
            {
                mock.get(5); result = "foo";
                mock.compareTo((List<String>) any);
                result = 0; }
        };
        assertEquals("foo", mock.get(5));
        assertEquals(0, mock.compareTo(new ArrayList<>()));
    }
}
