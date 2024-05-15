package com.baeldung.maths;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.text.DecimalFormat;

import org.apache.commons.math3.util.Precision;
import org.decimal4j.util.DoubleRounder;

public class Round {
    private static final double PI = 3.1415d;
    
    public static void main (String args[]) {
        System.out.println("PI: " + PI);
        System.out.printf("Value with 3 digits after decimal point %.3f %n", PI);
        // OUTPUTS: Value with 3 digits after decimal point 3.142
        DecimalFormat df = new DecimalFormat("###.###");
        System.out.println(df.format(PI));
        System.out.println(round(PI, 3));
        System.out.println(roundNotPrecise(PI, 3));
        System.out.println(roundAvoid(PI, 3));
        System.out.println(Precision.round(PI, 3));
        System.out.println(DoubleRounder.round(PI, 3));
    }
    
    public static double round(double value, int places) {
        if (places < 0) throw new IllegalArgumentException();
        
        BigDecimal bd = new BigDecimal(Double.toString(value));
        bd = bd.setScale(places, RoundingMode.HALF_UP);
        return bd.doubleValue();
    }
    
    public static double roundNotPrecise(double value, int places) {
        if (places < 0) throw new IllegalArgumentException();

        BigDecimal bd = new BigDecimal(value);
        bd = bd.setScale(places, RoundingMode.HALF_UP);
        return bd.doubleValue();
    }
    
    public static double roundAvoid(double value, int places) {
        double scale = Math.pow(10, places);
        double rounded = Math.round(value * scale) / scale;
        return rounded;
    }
}
