package com.baeldung.junit.main.test;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;

import org.junit.jupiter.api.Test;
import org.mockito.ArgumentCaptor;
import org.mockito.MockedStatic;
import org.mockito.Mockito;

public class TestStaticMain {

    @Test
    public void givenArgumentAsConsoleInput_WhenReadFromSubstitutedByteArrayInputStream_ThenSuccessfullyCalculate() throws IOException {
        String[] arguments = new String[] { "-i", "CONSOLE" };
        try (MockedStatic<StaticMain> mockedStatic = Mockito.mockStatic(StaticMain.class, Mockito.CALLS_REAL_METHODS);
            InputStream fips = new ByteArrayInputStream("1 2 3".getBytes())) {

            final InputStream original = System.in;

            //Reassigns the "standard" input stream
            System.setIn(fips);

            ArgumentCaptor<String> stringArgumentCaptor = ArgumentCaptor.forClass(String.class);

            StaticMain.main(arguments);

            mockedStatic.verify(() -> StaticMain.calculateSum(stringArgumentCaptor.capture()));

            System.setIn(original);
        }
    }

}
