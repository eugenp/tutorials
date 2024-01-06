package com.baeldung.algorithms.percentage;

import lombok.experimental.ExtensionMethod;
import java.math.BigDecimal;
import java.util.Scanner;

@ExtensionMethod(FastBigDecimalPercentage.class)
public class BigDecimalPercentageCalculator {
    public static void main(String[] args) {

        Scanner in = new Scanner(System.in);
        System.out.println("Enter obtained marks:");
        BigDecimal obtained = new BigDecimal(in.nextDouble());
        System.out.println("Enter total marks:");
        BigDecimal total = new BigDecimal(in.nextDouble());

        System.out.println("Percentage obtained :"+ obtained.toPercentageOf(total));
    }

}
