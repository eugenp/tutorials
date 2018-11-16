package com.baeldung.modifystring;


public class Main {

    public static void main(String[] args) {
        
        String myString = "abcdefgh";
        // Using String builder
        StringBuilder sBuilder = new StringBuilder(myString);
        sBuilder.setCharAt(4, 'x');
        System.out.println(sBuilder);

        // Using String buffer
        StringBuffer sBuffer = new StringBuffer(myString);
        sBuffer.setCharAt(4, 'x');
        System.out.println(sBuilder);

        // Creating new String instance
        String newString = myString.substring(0,4)+'x'+myString.substring(5);
        System.out.println(newString);

    }
}
