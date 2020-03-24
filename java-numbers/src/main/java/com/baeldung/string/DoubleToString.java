package com.baeldung.string;

import java.math.RoundingMode;
import java.text.DecimalFormat;
import java.text.NumberFormat;

public class DoubleToString {

    public static String truncateByCast(double d) {
        return String.valueOf((int) d);
    }

    public static String roundWithStringFormat(double d) {
        return String.format("%.0f", d);
    }

    public static String truncateWithNumberFormat(double d) {
        NumberFormat nf = NumberFormat.getInstance();
        nf.setMaximumFractionDigits(0);
        nf.setRoundingMode(RoundingMode.FLOOR);
        return nf.format(d);
    }

    public static String roundWithNumberFormat(double d) {
        NumberFormat nf = NumberFormat.getInstance();
        nf.setMaximumFractionDigits(0);
        return nf.format(d);
    }

    public static String truncateWithDecimalFormat(double d) {
        DecimalFormat df = new DecimalFormat("#,###");
        df.setRoundingMode(RoundingMode.FLOOR);
        return df.format(d);
    }

    public static String roundWithDecimalFormat(double d) {
        DecimalFormat df = new DecimalFormat("#,###");
        return df.format(d);
    }

    
}
