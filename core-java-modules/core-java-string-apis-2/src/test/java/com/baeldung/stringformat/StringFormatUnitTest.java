package com.baeldung.stringformat;

import static org.junit.Assert.assertThrows;
import static org.junit.jupiter.api.Assertions.assertEquals;

import java.text.NumberFormat;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.FormatFlagsConversionMismatchException;
import java.util.IllegalFormatException;
import java.util.IllegalFormatPrecisionException;
import java.util.Locale;
import java.util.MissingFormatArgumentException;

import org.junit.jupiter.api.Test;

public class StringFormatUnitTest {

    @Test
    public void whenFormat_thenCorrect() {
        String value = "Baeldung";
        String formatted = String.format("Welcome to %s!", value);
        assertEquals("Welcome to Baeldung!", formatted);

        boolean boolValue = true;
        String formattedBool = String.format("Boolean: %b", boolValue);
        assertEquals("Boolean: true", formattedBool);

        char charValue = 'A';
        String formattedChar = String.format("Character: %c", charValue);
        assertEquals("Character: A", formattedChar);

        int intValue = 255;
        String formattedInt = String.format("Decimal: %d", intValue);
        assertEquals("Decimal: 255", formattedInt);

        String formattedHex = String.format("Hex: %x", intValue);
        assertEquals("Hex: ff", formattedHex);

        double floatValue = 123.456789;
        String formattedFloat = String.format("Float: %.2f", floatValue);
        assertEquals("Float: 123.46", formattedFloat);

        String formattedExponential = String.format("Exponential: %e", floatValue);
        assertEquals("Exponential: 1.234568e+02", formattedExponential);

        String multipleFormat = String.format(
            "Boolean: %b, Character: %c, Decimal: %d, Hex: %x, Float: %.2f, Exponential: %e",
            boolValue, charValue, intValue, intValue, floatValue, floatValue
        );

        assertEquals(
            "Boolean: true, Character: A, Decimal: 255, Hex: ff, Float: 123.46, Exponential: 1.234568e+02",
            multipleFormat
        );
    }

    @Test
    public void whenIncompatibleFlags_thenFormatFlagsConversionMismatchExceptionThrown() {
        assertThrows(FormatFlagsConversionMismatchException.class, () -> {String formatted = String.format("%+s", "Baeldung");});
    }

    @Test
    public void whenInvalidPrecisionForType_thenIllegalFormatPrecisionExceptionThrown() {
        assertThrows(IllegalFormatPrecisionException.class, () -> {
            String formatted = String.format("%.2d", 123);
        });
    }

    @Test
    public void whenMissingFormatArgument_thenMissingFormatArgumentExceptionThrown() {
        assertThrows(MissingFormatArgumentException.class, () -> {
            String formatted = String.format("Welcome to %s and %s!", "Baeldung");
        });
    }

    @Test
    public void whenNumberFormatWithLocales_thenCorrect() {
        String frenchFormatted = String.format(Locale.FRANCE, "%,f", 1234567.89);
        assertEquals("1 234 567,890000", frenchFormatted);
        String germanFormatted = String.format(Locale.GERMANY, "%,.2f", 1234567.89);
        assertEquals("1.234.567,89", germanFormatted);
    }

    @Test
    public void whenDateFormatWithLocales_thenCorrect() {
        LocalDate date = LocalDate.of(2023, 9, 30);
        DateTimeFormatter usFormatter = DateTimeFormatter.ofPattern("MM/dd/yyyy", Locale.US);
        String usFormatted = date.format(usFormatter);
        assertEquals("09/30/2023", usFormatted);
        DateTimeFormatter germanFormatter = DateTimeFormatter.ofPattern("dd.MM.yyyy", Locale.GERMANY);
        String germanFormatted = date.format(germanFormatter);
        assertEquals("30.09.2023", germanFormatted);
    }

    @Test
    public void whenCurrencyFormatWithLocales_thenCorrect() {
        NumberFormat usCurrencyFormat = NumberFormat.getCurrencyInstance(Locale.US);
        String usFormatted = usCurrencyFormat.format(1000);
        assertEquals("$1,000.00", usFormatted);

        NumberFormat frenchCurrencyFormat = NumberFormat.getCurrencyInstance(Locale.FRANCE);
        String frenchFormatted = frenchCurrencyFormat.format(1000);
        assertEquals("1 000,00 €", frenchFormatted);
    }
}
