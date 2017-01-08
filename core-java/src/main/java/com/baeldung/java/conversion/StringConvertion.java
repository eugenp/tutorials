package com.baeldung.java.conversion;

public class StringConvertion {

    /**
     * 
     * @param stringToConvert
     * @return
     */
    public char[] convertToCharArray(String stringToConvert) {
        char[] charArray = stringToConvert.toCharArray();
        return charArray;

    }

    /**
     * 
     * @param charArray
     * @return
     */
    public String convertCharArrayToStringWithConstructor(char[] charArray) {
        String convertedString = new String(charArray);
        return convertedString;
    }

    /**
     * 
     * @param charArray
     * @return
     */
    public String convertCharArrayToStringWithValueOf(char[] charArray) {
        String convertedString = String.valueOf(charArray);
        return convertedString;
    }

}
