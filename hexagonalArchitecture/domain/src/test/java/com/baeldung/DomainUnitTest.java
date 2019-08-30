package com.baeldung;

import com.baeldung.service.NamingPort;
import com.baeldung.service.OutputStreamingPort;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.*;


class DomainUnitTest {

    @Test
    void testGreeting() {
        //mock the output stream
        OutputStreamingPort outputStreamingPort = mock(OutputStreamingPort.class);
        doAnswer((i) -> {
            Assertions.assertEquals("Hello World", i.getArgument(0));
            return null;
        }).when(outputStreamingPort).write(anyString());
        //mock the naming port
        NamingPort namingPort = mock(NamingPort.class);
        when(namingPort.getName()).thenReturn("World");

        new Domain(outputStreamingPort, namingPort).greeting();
    }
}
