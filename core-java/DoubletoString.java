package com.baeldung.doubletostring;

import java.math.RoundingMode;
import java.text.DecimalFormat;

public class DoubletoString {

    public static void main(String[] args) {

        double doubleValue = 345.56;

        System.out.println(String.valueOf((int) doubleValue));

        System.out.println(String.format("%.0f", doubleValue));

        doubleValue = Math.floor(doubleValue);
        DecimalFormat df = new DecimalFormat("#");
        df.setRoundingMode(RoundingMode.FLOOR);
        System.out.println(df.format(doubleValue));

    }

}
