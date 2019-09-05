package com.baeldung;

import com.baeldung.service.GreetingPort;
import com.baeldung.service.OutputStreamingPort;
import com.baeldung.service.impl.GreetingPortHelloWorldAdapter;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;

import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.doAnswer;
import static org.mockito.Mockito.mock;


class HelloHexagonApplicationUnitTest {

    @Test
    void helloWorldTOConsole() {
        //to change default output stream
        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
        PrintStream printStream = new PrintStream(byteArrayOutputStream);
        System.setOut(printStream);
        //

        GreetingPort greetingPortHelloWorldAdapter = new GreetingPortHelloWorldAdapter();
        greetingPortHelloWorldAdapter.greet();

        //retrieve what has been written to outputStream
        String s = byteArrayOutputStream.toString();
        Assertions.assertEquals(s, "Hello World\n");
    }

    @Test
    void helloWorldToToMockOutputStreamingPort() {

        //mock OutputStreamingPort
        OutputStreamingPort outputStreamingPortMockAdapter = mock(OutputStreamingPort.class);
        doAnswer(invocation -> {
            Assertions.assertEquals("Hello World", invocation.getArgument(0));
            return null;
        }).when(outputStreamingPortMockAdapter).write(anyString());
        //

        GreetingPortHelloWorldAdapter greetingPortHelloWorldAdapter = new GreetingPortHelloWorldAdapter(outputStreamingPortMockAdapter);
        greetingPortHelloWorldAdapter.greet();
    }

    @Test
    void helloHexagonFromMockGreetingPortToToMockOutputStreamingPort() {

        //mock OutputStreamingPort
        OutputStreamingPort outputStreamingPortMockAdapter = mock(OutputStreamingPort.class);
        doAnswer(invocation -> {
            Assertions.assertEquals("Hello World From Mocked Greeting Port Adapter", invocation.getArgument(0));
            return null;
        }).when(outputStreamingPortMockAdapter).write(anyString());
        //

        //mock GreetingPort
        GreetingPort greetingPort = mock(GreetingPort.class);
        doAnswer(invocation -> {
            outputStreamingPortMockAdapter.write("Hello World From Mocked Greeting Port Adapter");
            return null;
        }).when(greetingPort).greet();
        //

        greetingPort.greet();
    }


}