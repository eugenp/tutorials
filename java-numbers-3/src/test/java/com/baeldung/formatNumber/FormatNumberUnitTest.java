package com.baeldung.formatNumber;

import org.junit.Test;

import java.util.Locale;

import static com.baeldung.formatNumber.FormatNumber.*;
import static org.assertj.core.api.Assertions.*;

public class FormatNumberUnitTest {
    private static final double D = 4.2352989244d;
    private static final double F = 8.6994540927d;

    @Test
    public void givenDecimalNumber_whenFormatNumber_withBigDecimal_thenGetExpectedResult() {
        assertThat(withBigDecimal(D, 2)).isEqualTo(4.24);
        assertThat(withBigDecimal(D, 3)).isEqualTo(4.235);
        assertThat(withBigDecimal(F, 2)).isEqualTo(8.7);
        assertThat(withBigDecimal(F, 3)).isEqualTo(8.699);
    }

    @Test
    public void givenDecimalNumber_whenFormatNumber_withDecimalFormat_thenGetExpectedResult() {
        assertThat(withDecimalFormatLocal(D)).isEqualTo(4.235);
        assertThat(withDecimalFormatLocal(F)).isEqualTo(8.699);

        assertThat(withDecimalFormatPattern(D, 2)).isEqualTo(4.24);
        assertThat(withDecimalFormatPattern(D, 3)).isEqualTo(4.235);
        assertThat(withDecimalFormatPattern(F, 2)).isEqualTo(8.7);
        assertThat(withDecimalFormatPattern(F, 3)).isEqualTo(8.699);
    }

    @Test
    public void givenDecimalNumber_whenFormatNumber_withStringFormat_thenGetExpectedResult() {
        assertThat(withStringFormat(D, 2)).isEqualTo("4.24");
        assertThat(withStringFormat(D, 3)).isEqualTo("4.235");
        assertThat(withStringFormat(F, 2)).isEqualTo("8.70");
        assertThat(withStringFormat(F, 3)).isEqualTo("8.699");
    }

    @Test
    public void givenDecimalNumber_whenFormatNumber_withMathRound_thenGetExpectedResult() {
        assertThat(withMathRound(D, 2)).isEqualTo(4.24);
        assertThat(withMathRound(D, 3)).isEqualTo(4.235);
        assertThat(withMathRound(F, 2)).isEqualTo(8.7);
        assertThat(withMathRound(F, 3)).isEqualTo(8.699);
    }

    @Test
    public void givenIntegerNumber_whenFormatNumber_byPaddingOutZeros_thenGetExpectedResult() {
        int value = 1;
        assertThat(byPaddingOutZeros(value, 3)).isEqualTo("001");
    }

    @Test
    public void givenIntegerNumber_whenFormatNumber_withTwoDecimalPlaces_thenGetExpectedResult() {
        int value = 12;
        assertThat(withTwoDecimalPlaces(value)).isEqualTo(12.00);
    }

    @Test
    public void givenIntegerNumber_whenFormatNumber_withLargeIntegers_thenGetExpectedResult() {
        int value = 123456789;
        assertThat(withLargeIntegers(value)).isEqualTo("123,456,789");
    }

    @Test
    public void givenDecimalNumber_whenFormatNumber_forPercentages_thenGetExpectedResult() {
        double value = 25f / 100f;
        assertThat(forPercentages(value, new Locale("en", "US"))).isEqualTo("25%");
        assertThat(forPercentages(value, new Locale("pl", "PL"))).isEqualTo("25%");
    }

    @Test
    public void givenCurrency_whenFormatNumber_currencyWithChosenLocalisation_thenGetExpectedResult() {
        double value = 23_500;
        assertThat(currencyWithChosenLocalisation(value, new Locale("en", "US"))).isEqualTo("$23,500.00");
        assertThat(currencyWithChosenLocalisation(value, new Locale("zh", "CN"))).isEqualTo("￥23,500.00");
        assertThat(currencyWithChosenLocalisation(value, new Locale("pl", "PL"))).isEqualTo("23 500 zł");
    }

    @Test
    public void givenCurrency_whenFormatNumber_currencyWithDefaultLocalisation_thenGetExpectedResult() {
        double value = 23_500;
        assertThat(currencyWithDefaultLocalisation(value)).isEqualTo("£23,500.00");
    }

}
