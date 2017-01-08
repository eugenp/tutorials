package com.baeldung.java.conversion;

public class StringConvertion {

    public char[] convertToCharArray(String stringToConvert) {
        char[] charArray = stringToConvert.toCharArray();
        return charArray;

    }

    public String convertCharArrayToStringWithConstructor(char[] charArray) {
        String convertedString = new String(charArray);
        return convertedString;
    }

    public String convertCharArrayToStringWithValueOf(char[] charArray) {
        String convertedString = String.valueOf(charArray);
        return convertedString;
    }

}
