package com.baeldung.helpervsutilityclasses;

class MyHelperClass {
    public double discount;
    public MyHelperClass(double discount) {
        if (discount > 0 && discount < 1) {
            this.discount = discount;
        }
    }
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
