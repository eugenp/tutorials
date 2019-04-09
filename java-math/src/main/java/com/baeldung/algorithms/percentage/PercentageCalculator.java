package com.baeldung.algorithms.percentage;

import java.util.Scanner;

public class PercentageCalculator {

    public double calculatePercentage(double obtained,double total){
        return obtained*100/total;
    }

    public static void main(String[] args) {
        PercentageCalculator pc = new PercentageCalculator();
        Scanner in = new Scanner(System.in);
        System.out.println("Enter obtained marks:");
        double obtained = in.nextDouble();
        System.out.println("Enter total marks:");
         double total =in.nextDouble();
        System.out.println("Percentage obtained :"+pc.calculatePercentage(obtained,total));
    }

}
