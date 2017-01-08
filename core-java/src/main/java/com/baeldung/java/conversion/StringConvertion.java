package com.baeldung.java.conversion;

public class StringConvertion {

<<<<<<< HEAD
=======
    /**
     * 
     * @param stringToConvert
     * @return
     */
>>>>>>> b09122accf977a5d0d13a2b7aeb8907bad022885
    public char[] convertToCharArray(String stringToConvert) {
        char[] charArray = stringToConvert.toCharArray();
        return charArray;

    }

<<<<<<< HEAD
=======
    /**
     * 
     * @param charArray
     * @return
     */
>>>>>>> b09122accf977a5d0d13a2b7aeb8907bad022885
    public String convertCharArrayToStringWithConstructor(char[] charArray) {
        String convertedString = new String(charArray);
        return convertedString;
    }

<<<<<<< HEAD
=======
    /**
     * 
     * @param charArray
     * @return
     */
>>>>>>> b09122accf977a5d0d13a2b7aeb8907bad022885
    public String convertCharArrayToStringWithValueOf(char[] charArray) {
        String convertedString = String.valueOf(charArray);
        return convertedString;
    }

}
