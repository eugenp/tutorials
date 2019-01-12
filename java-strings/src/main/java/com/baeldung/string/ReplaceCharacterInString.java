package com.baeldung.string;

public class ReplaceCharacterInString {

    public String replaceCharSubstring(String str, char ch, int index) {
        String myString = str.substring(0, index) + ch + str.substring(index + 1);
        return myString;
    }

    public String replaceCharUsingCharArray(String str, char ch, int index) {
        char[] chars = str.toCharArray();
        chars[index] = ch;
        return String.valueOf(chars);
    }


    public String replaceCharStringBuilder(String str, char ch, int index) {
        StringBuilder myString = new StringBuilder(str);
        myString.setCharAt(index, ch);
        return myString.toString();
    }

    public String replaceCharStringBuffer(String str, char ch, int index) {
        StringBuffer myString = new StringBuffer(str);
        myString.setCharAt(index, ch);
        return myString.toString();
    }

}
