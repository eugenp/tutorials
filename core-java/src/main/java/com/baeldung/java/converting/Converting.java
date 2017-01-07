package com.baeldung.java.converting;

import org.apache.commons.lang3.StringUtils;

public class Converting {
    public static void main(String[] args) {
        String stringToConvert = "Java";
        char[] charArrayToConvert = {'J', 'a', 'v', 'a'};

        char[] convertedString = stringToConvert.toCharArray();
        System.out.println("convertedString: " + convertedString[0]
                + convertedString[1]
                + convertedString[2]
                + convertedString[3]);

        String convertedCharArray = new String(charArrayToConvert);
        System.out.println("convertedCharArray: " + convertedCharArray);
        String convertedCharArray1 = String.valueOf(charArrayToConvert);
        System.out.println("convertedCharArray1: " + convertedCharArray1);
        String convertedCharArray2 = String.copyValueOf(charArrayToConvert);
        System.out.println("convertedCharArray2: " + convertedCharArray2);
        char separator = ' ';
        String convertedCharArray3 = StringUtils.join(charArrayToConvert, separator);
        System.out.println("convertedCharArray3: " + convertedCharArray3);
    }
}
