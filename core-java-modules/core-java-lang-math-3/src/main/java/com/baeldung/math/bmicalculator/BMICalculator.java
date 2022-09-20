package com.baeldung.math.bmicalculator;

public class BMICalculator {

    public static String calculateBMI(double weight, double height) {

        double bmi = weight / (height * height);

        if (bmi < 18.5)
            return "Underweight";
        else if (bmi < 25)
            return "Normal";
        else if (bmi < 30)
            return "Overweight";
        else
            return "Obese";
    }
}
