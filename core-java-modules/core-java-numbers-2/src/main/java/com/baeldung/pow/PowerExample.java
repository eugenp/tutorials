package com.baeldung.pow;

import java.text.DecimalFormat;

public class PowerExample {

    public static void main(String[] args) {

        int intResult = (int) Math.pow(2, 3);
        System.out.println("Math.pow(2, 3) = " + intResult);

        double dblResult = Math.pow(4.2, 3);
        System.out.println("Math.pow(4.2, 3) = " + Math.pow(4.2, 3));

        DecimalFormat df = new DecimalFormat(".00");
        System.out.println("Math.pow(4.2, 3) rounded = " + df.format(dblResult));

    }
}
