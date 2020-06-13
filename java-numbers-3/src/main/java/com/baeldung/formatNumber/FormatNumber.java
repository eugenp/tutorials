package com.baeldung.formatNumber;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.util.Locale;

public class FormatNumber {
    public static double withBigDecimal(double value, int places) {
        if (places < 0)
            throw new IllegalArgumentException();

        BigDecimal bigDecimal = new BigDecimal(value);
        bigDecimal = bigDecimal.setScale(places, RoundingMode.HALF_UP);
        return bigDecimal.doubleValue();
    }

    public static double withMathRound(double value, int places) {
        double scale = Math.pow(10, places);
        return Math.round(value * scale) / scale;
    }

    public static double withDecimalFormatPattern(double value, int places) {
        DecimalFormat df2 = new DecimalFormat("#,###,###,##0.00");
        DecimalFormat df3 = new DecimalFormat("#,###,###,##0.000");
        if (places == 2)
            return new Double(df2.format(value));
        else if (places == 3)
            return new Double(df3.format(value));
        else
            throw new IllegalArgumentException();
    }

    public static double withDecimalFormatLocal(double value) {
        DecimalFormat df = (DecimalFormat) NumberFormat.getNumberInstance(Locale.getDefault());
        return new Double(df.format(value));
    }

    public static String withStringFormat(double value, int places) {
        return String.format("%." + places + "f", value);
    }

    public static String byPaddingOutZeros(int value, int paddingLength) {
        return String.format("%0" + paddingLength + "d", value);
    }

    public static double withTwoDecimalPlaces(double value) {
        DecimalFormat df = new DecimalFormat("#.00");
        return new Double(df.format(value));
    }

    public static String withLargeIntegers(double value) {
        DecimalFormat df = new DecimalFormat("###,###,###");
        return df.format(value);
    }

    public static String forPercentages(double value, Locale localisation) {
        NumberFormat nf = NumberFormat.getPercentInstance(localisation);
        return nf.format(value);
    }

    public static String currencyWithChosenLocalisation(double value, Locale localisation) {
        NumberFormat nf = NumberFormat.getCurrencyInstance(localisation);
        return nf.format(value);
    }

    public static String currencyWithDefaultLocalisation(double value) {
        NumberFormat nf = NumberFormat.getCurrencyInstance();
        return nf.format(value);
    }
}

