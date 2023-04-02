package com.baeldung.helperVsUtilityClasses;

class MyHelperClass {

    public double discount = 0.10;
    public double discountedPrice(double price) {
        return price - (price * discount);
    }

    public static int getMaxNumber(int[] numbers) {
        if (numbers.length == 0) {
            throw new IllegalArgumentException("Ensure array is not empty");
        }
        int max = numbers[0];
        for (int i = 1; i < numbers.length; i++) {
            if (numbers[i] > max) {
                max = numbers[i];
            }
        }
        return max;
    }

    public static int getMinNumber(int[] numbers) {
        if (numbers.length == 0) {
            throw new IllegalArgumentException("Ensure array is not empty");
        }
        int min = numbers[0];
        for (int i = 1; i < numbers.length; i++) {
            if (numbers[i] < min) {
                min = numbers[i];
            }
        }
        return min;
    }

}
