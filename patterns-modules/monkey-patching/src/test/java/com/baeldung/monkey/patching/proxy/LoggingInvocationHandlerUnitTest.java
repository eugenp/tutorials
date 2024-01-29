package com.baeldung.monkey.patching.proxy;

import com.baeldung.monkey.patching.converter.MoneyConverter;
import com.baeldung.monkey.patching.converter.MoneyConverterImpl;
import org.junit.Test;
import org.junit.jupiter.api.Assertions;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;
import java.lang.reflect.Proxy;

import static org.junit.Assert.assertTrue;

public class LoggingInvocationHandlerUnitTest {

    @Test
    public void whenMethodCalled_thenSurroundedByLogs() {
        ByteArrayOutputStream logOutputStream = new ByteArrayOutputStream();
        System.setOut(new PrintStream(logOutputStream));
        MoneyConverter moneyConverter = new MoneyConverterImpl();
        MoneyConverter proxy = (MoneyConverter) Proxy.newProxyInstance(
                MoneyConverter.class.getClassLoader(),
                new Class[]{MoneyConverter.class},
                new LoggingInvocationHandler(moneyConverter)
        );

        double result = proxy.convertEURtoUSD(10);

        Assertions.assertEquals(11, result);
        String logOutput = logOutputStream.toString();
        assertTrue(logOutput.contains("Before method: convertEURtoUSD"));
        assertTrue(logOutput.contains("After method: convertEURtoUSD"));
    }
}
