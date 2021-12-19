package com.baeldung.equalsvscontentequals;

public class StringEqualsVsContentEqualsCheck {

    public static void main(String[] args) {

        String actualString = "baeldung";
        String identicalString = "baeldung";
        String stringInUpperCase = "BAELDUNG";
        String stringInWrongSequence = "baledung";
        StringBuffer buffer = new StringBuffer("baeldung");
        CharSequence charSequence = new StringBuffer("baeldung");

        /*check identical string-both methods return true*/
        System.out.println(actualString.equals(identicalString));
        System.out.println(actualString.contentEquals(identicalString));

        /*check string in upper case-both methods return false*/
        System.out.println(actualString.equals(stringInUpperCase));
        System.out.println(actualString.contentEquals(stringInUpperCase));

        /*check two different string-both methods return false*/
        System.out.println(actualString.equals(stringInWrongSequence));
        System.out.println(actualString.contentEquals(stringInWrongSequence));

        /*check a string buffer-equals return false but contentEquals return true*/
        System.out.println(actualString.equals(buffer));
        System.out.println(actualString.contentEquals(buffer));

        /*check a string charSequence-equals return false but contentEquals return true*/
        System.out.println(actualString.equals(charSequence));
        System.out.println(actualString.contentEquals(charSequence));

    }

}
