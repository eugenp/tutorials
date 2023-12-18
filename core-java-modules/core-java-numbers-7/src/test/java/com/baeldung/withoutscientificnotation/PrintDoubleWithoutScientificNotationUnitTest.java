package com.baeldung.withoutscientificnotation;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;
import java.math.BigDecimal;
import java.text.DecimalFormat;
import java.util.Locale;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class PrintDoubleWithoutScientificNotationUnitTest {

    private final PrintStream standardOut = System.out;
    private final ByteArrayOutputStream outputStreamCaptor = new ByteArrayOutputStream();

    @BeforeEach
    public void setUp() {
        System.setOut(new PrintStream(outputStreamCaptor));
        Locale.setDefault(Locale.US);
    }

    @AfterEach
    public void tearDown() {
        System.setOut(standardOut);
    }

    @Test
    void givenLargeNumber_whenPrintWithDecimalFormat_thenOutputIsWithoutScientificNotation() {

        DecimalFormat df = new DecimalFormat("#.###########");
        double largeNumber = 256450000d;
        System.out.println("Large Number: " + df.format(largeNumber));

        Assertions.assertEquals("Large Number: 256450000", outputStreamCaptor.toString()
          .trim());
    }

    @Test
    void givenSmallNumber_whenPrintWithDecimalFormat_thenOutputIsWithoutScientificNotation() {

        DecimalFormat df = new DecimalFormat("#.###########");
        double smallNumber = 0.0000046d;
        System.out.println("Small Number: " + df.format(smallNumber));

        Assertions.assertEquals("Small Number: 0.0000046", outputStreamCaptor.toString()
          .trim());
    }

    @Test
    void givenLargeNumber_whenPrintWithPrintf_thenOutputIsWithoutScientificNotation() {

        double largeNumber = 256450000d;
        System.out.printf("Large Number: %.7f", largeNumber);

        Assertions.assertEquals("Large Number: 256450000.0000000", outputStreamCaptor.toString()
          .trim());
    }

    @Test
    void givenSmallNumber_whenPrintWithPrintf_thenOutputIsWithoutScientificNotation() {

        double smallNumber = 0.0000046d;
        System.out.printf("Small Number: %.7f", smallNumber);

        Assertions.assertEquals("Small Number: 0.0000046", outputStreamCaptor.toString()
          .trim());
    }

    @Test
    void givenLargeNumber_whenPrintWithBigDecimal_thenOutputIsWithoutScientificNotation() {

        double largeNumber = 256450000d;
        System.out.println("Large Number: " + BigDecimal.valueOf(largeNumber).toPlainString());

        Assertions.assertEquals("Large Number: 256450000", outputStreamCaptor.toString()
          .trim());
    }

    @Test
    void givenSmallNumber_whenPrintWithBigDecimal_thenOutputIsWithoutScientificNotation() {

        double smallNumber = 0.0000046d;
        System.out.println("Small Number: " + BigDecimal.valueOf(smallNumber).toPlainString());

        Assertions.assertEquals("Small Number: 0.0000046", outputStreamCaptor.toString()
          .trim());
    }

    @Test
    void givenLargeNumber_whenPrintWithStringFormat_thenOutputIsWithoutScientificNotation() {

        double largeNumber = 256450000d;
        String formattedLargeNumber = String.format("%.7f", largeNumber);
        System.out.println("Large Number: " + formattedLargeNumber);

        Assertions.assertEquals("Large Number: 256450000.0000000", outputStreamCaptor.toString()
          .trim());
    }

    @Test
    void givenSmallNumber_whenPrintWithStringFormat_thenOutputIsWithoutScientificNotation() {

        double smallNumber = 0.0000046d;
        String formattedSmallNumber = String.format("%.7f", smallNumber);
        System.out.println("Small Number: " + formattedSmallNumber);

        Assertions.assertEquals("Small Number: 0.0000046", outputStreamCaptor.toString()
          .trim());
    }
}
