package org.baeldung.mocks.jmockit;

import static org.junit.Assert.assertEquals;

import org.junit.Test;
import org.junit.runner.RunWith;

import mockit.Deencapsulation;
import mockit.Mock;
import mockit.MockUp;
import mockit.Tested;
import mockit.integration.junit4.JMockit;

@RunWith(JMockit.class)
public class AdvancedCollaboratorTest {

    @Tested
    private AdvancedCollaborator mock;

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
    public void testToCallPrivateMethodsDirectly(){
        Object value = Deencapsulation.invoke(mock, "privateMethod");
        assertEquals("default:", value);
    }
}
