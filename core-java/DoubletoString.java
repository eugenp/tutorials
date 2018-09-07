package com.baeldung.doubletostring;

import java.math.RoundingMode;
import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.util.Locale;

public class DoubletoString {

    public static void main(String[] args) {

        double doubleValue = 345.56;

        System.out.println(String.valueOf((int) doubleValue));

        System.out.println(String.format("%.0f", doubleValue));

        doubleValue = Math.floor(doubleValue);
        DecimalFormat df = new DecimalFormat("#");
        df.setRoundingMode(RoundingMode.FLOOR);
        System.out.println(df.format(doubleValue));
        
        Locale enlocale = new Locale("en", "US");
        String pattern = "###,##";
        df = (DecimalFormat) NumberFormat.getNumberInstance(enlocale);
        df.applyPattern(pattern);
        String format = df.format(doubleValue);
        System.out.println(format);

        Locale dalocale = new Locale("da", "DK");
        df = (DecimalFormat) NumberFormat.getNumberInstance(dalocale);
        df.applyPattern(pattern);
        System.out.println(df.format(doubleValue));


    }

}
