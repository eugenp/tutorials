package com.baeldung.monkey.patching.aop;

import com.baeldung.monkey.patching.converter.MoneyConverter;
import org.junit.Test;
import org.junit.jupiter.api.Assertions;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;

import static org.junit.Assert.assertTrue;

@SpringBootTest
@RunWith(SpringRunner.class)
public class LoggingAspectIntegrationTest {

    @Autowired
    private MoneyConverter moneyConverter;

    @Test
    public void whenMethodCalled_thenSurroundedByLogs() {
        ByteArrayOutputStream logOutputStream = new ByteArrayOutputStream();
        System.setOut(new PrintStream(logOutputStream));

        double result = moneyConverter.convertEURtoUSD(10);

        Assertions.assertEquals(11, result);
        String logOutput = logOutputStream.toString();
        assertTrue(logOutput.contains("Before method: convertEURtoUSD"));
        assertTrue(logOutput.contains("After method: convertEURtoUSD"));
    }
}
