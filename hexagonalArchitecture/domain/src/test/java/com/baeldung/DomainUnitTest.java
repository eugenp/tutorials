package com.baeldung;

import com.baeldung.service.GreetingPort;
import com.baeldung.service.NamingPort;
import com.baeldung.service.OutputStreamingPort;
import com.baeldung.service.TranslatingPort;
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
        //mock the greeting port
        GreetingPort greetingPort = mock(GreetingPort.class);
        when(greetingPort.getGreetingPhrase()).thenReturn("Hello");
        when(greetingPort.getDesiredLanguage()).thenReturn("en");
        //mock the translating port
        TranslatingPort translatingPort = mock(TranslatingPort.class);
        when(translatingPort.translate(anyString(), anyString())).thenReturn("Hello");

        new Domain(outputStreamingPort, namingPort, greetingPort, translatingPort).greeting();
    }}
