package com.baeldung.formatNumber;

import static org.assertj.core.api.Assertions.*;

import static com.baeldung.formatNumber.FormatNumber.byPaddingZeros;
import static com.baeldung.formatNumber.FormatNumber.currencyWithChosenLocalisation;
import static com.baeldung.formatNumber.FormatNumber.forPercentages;
import static com.baeldung.formatNumber.FormatNumber.formatScientificNotation;
import static com.baeldung.formatNumber.FormatNumber.formatScientificNotationWithMinChars;
import static com.baeldung.formatNumber.FormatNumber.withBigDecimal;
import static com.baeldung.formatNumber.FormatNumber.withDecimalFormatLocal;
import static com.baeldung.formatNumber.FormatNumber.withDecimalFormatPattern;
import static com.baeldung.formatNumber.FormatNumber.withLargeIntegers;
import static com.baeldung.formatNumber.FormatNumber.withMathRound;
import static com.baeldung.formatNumber.FormatNumber.withStringFormat;
import static com.baeldung.formatNumber.FormatNumber.withTwoDecimalPlaces;

import java.util.Locale;

import org.junit.Test;

public class FormatNumberUnitTest {
    private static final double D = 4.2352989244d;
    private static final double F = 8.6994540927d;

    @Test
    public void givenDecimalNumber_whenFormatNumberWithBigDecimal_thenGetExpectedResult() {
        assertThat(withBigDecimal(D, 2)).isEqualTo(4.24);
        assertThat(withBigDecimal(D, 3)).isEqualTo(4.235);
        assertThat(withBigDecimal(F, 2)).isEqualTo(8.7);
        assertThat(withBigDecimal(F, 3)).isEqualTo(8.699);
    }

    @Test
    public void givenDecimalNumber_whenFormatNumberWithDecimalFormat_thenGetExpectedResult() {
        assertThat(withDecimalFormatLocal(D)).isEqualTo(4.235);
        assertThat(withDecimalFormatLocal(F)).isEqualTo(8.699);

        assertThat(withDecimalFormatPattern(D, 2)).isEqualTo(4.24);
        assertThat(withDecimalFormatPattern(D, 3)).isEqualTo(4.235);
        assertThat(withDecimalFormatPattern(F, 2)).isEqualTo(8.7);
        assertThat(withDecimalFormatPattern(F, 3)).isEqualTo(8.699);
    }

    @Test
    public void givenDecimalNumber_whenFormatNumberWithStringFormat_thenGetExpectedResult() {
        assertThat(withStringFormat(D, 2)).isEqualTo("4.24");
        assertThat(withStringFormat(D, 3)).isEqualTo("4.235");
        assertThat(withStringFormat(F, 2)).isEqualTo("8.70");
        assertThat(withStringFormat(F, 3)).isEqualTo("8.699");
    }

    @Test
    public void givenDecimalNumber_whenFormatNumberWithMathRound_thenGetExpectedResult() {
        assertThat(withMathRound(D, 2)).isEqualTo(4.24);
        assertThat(withMathRound(D, 3)).isEqualTo(4.235);
        assertThat(withMathRound(F, 2)).isEqualTo(8.7);
        assertThat(withMathRound(F, 3)).isEqualTo(8.699);
    }

    @Test
    public void givenIntegerNumber_whenFormatNumberByPaddingOutZeros_thenGetExpectedResult() {
        int value = 1;
        assertThat(byPaddingZeros(value, 3)).isEqualTo("001");
    }

    @Test
    public void givenIntegerNumber_whenFormatNumberWithTwoDecimalPlaces_thenGetExpectedResult() {
        int value = 12;
        assertThat(withTwoDecimalPlaces(value)).isEqualTo(12.00);
    }

    @Test
    public void givenIntegerNumber_whenFormatNumberWithLargeIntegers_thenGetExpectedResult() {
        int value = 123456789;
        assertThat(withLargeIntegers(value)).isEqualTo("123,456,789");
    }

    @Test
    public void givenDecimalNumber_whenFormatNumberForPercentages_thenGetExpectedResult() {
        double value = 25f / 100f;
        assertThat(forPercentages(value, new Locale("en", "US"))).isEqualTo("25%");
        assertThat(forPercentages(value, new Locale("pl", "PL"))).isEqualTo("25%");
    }

    @Test
    public void givenCurrency_whenFormatNumberCurrencyWithChosenLocalisation_thenGetExpectedResult() {
        double value = 23_500;
        assertThat(currencyWithChosenLocalisation(value, new Locale("en", "US"))).isEqualTo("$23,500.00");
        assertThat(currencyWithChosenLocalisation(value, new Locale("zh", "CN"))).isEqualTo("¥23,500.00");
        assertThat(currencyWithChosenLocalisation(value, new Locale("pl", "PL"))).isEqualTo("23 500,00 zł");
    }

    @Test
    public void givenDouble_whenFormatMethodIsInvoked_thenGetItInScientificNotation() {
        Locale us = new Locale("en", "US");
        assertThat(formatScientificNotation(3.14159, us)).isEqualTo("3.142E+00");
        assertThat(formatScientificNotation(0.0123456, us)).isEqualTo("1.235E-02");
        assertThat(formatScientificNotation(1111111, us)).isEqualTo("1.111E+06");
    }

    @Test
    public void givenDouble_whenFormatMethodIsInvokedWithMinChars_thenGetItInScientificNotation() {
        Locale us = new Locale("en", "US");
        assertThat(formatScientificNotationWithMinChars(3.14159, us)).isEqualTo("  3.1416E+00");
        assertThat(formatScientificNotationWithMinChars(0.0123456, us)).isEqualTo("  1.2346E-02");
        assertThat(formatScientificNotationWithMinChars(1111111, us)).isEqualTo("  1.1111E+06");
    }

}
