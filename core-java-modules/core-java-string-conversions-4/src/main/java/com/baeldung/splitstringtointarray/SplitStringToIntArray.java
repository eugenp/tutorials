package com.baeldung.splitstringtointarray;

public class SplitStringToIntArray {

    public int[] convert(String numbers, String delimiterRegex) {
        if (numbers == null || numbers.isEmpty()) {
            return new int[0];
        }

        String[] parts = numbers.split(delimiterRegex);
        int[] intArray = new int[parts.length];

        for (int i = 0; i < parts.length; i++) {
            intArray[i] = Integer.parseInt(parts[i].trim());
        }

        return intArray;
    }
}
