package com.baeldung.easymock;

import static org.easymock.EasyMock.*;
import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.Test;

public class MatchersUnitTest {

    @Test
    void whenUsingIsA_thenMatchesTypeAndRejectsNull() {
        Service mock = mock(Service.class);
        mock.process(isA(String.class));
        expectLastCall().times(1);
        replay(mock);

        mock.process("test");
        verify(mock);
    }

    @Test
    void whenUsingIsAWithInheritance_thenMatchesSubclass() {
        Service mock = mock(Service.class);
        mock.handleRequest(isA(Request.class));
        expectLastCall().times(2);
        replay(mock);

        mock.handleRequest(new Request("normal"));
        mock.handleRequest(new SpecialRequest());
        verify(mock);
    }

    @Test
    void whenUsingIsAWithNull_thenFails() {
        Service mock = mock(Service.class);
        mock.process(isA(String.class));
        expectLastCall().times(1);
        replay(mock);

        assertThrows(AssertionError.class, () -> {
            mock.process(null);
            verify(mock);
        });
    }

    @Test
    void whenUsingAnyObject_thenMatchesNullAndAnyType() {
        Service mock = mock(Service.class);
        mock.process(anyObject());
        expectLastCall().times(2);
        replay(mock);

        mock.process("test");
        mock.process(null);
        verify(mock);
    }

    interface Service {
        void process(String input);
        void handleRequest(Request request);
    }

    class Request {
        private String type;
        Request(String type) {
            this.type = type;
        }
    }

    class SpecialRequest extends Request {
        SpecialRequest() {
            super("special");
        }
    }
}
