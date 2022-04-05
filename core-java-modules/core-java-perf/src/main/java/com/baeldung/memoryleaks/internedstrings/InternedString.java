package com.baeldung.memoryleaks.internedstrings;

public class InternedString {
    private static final String FILEPATH = "C:\\bigstring.txt";
    
    public void readString() {
        String s1 = ReadStringFromFileUtil.read(FILEPATH).intern();
        String s2 = ReadStringFromFileUtil.read(FILEPATH).intern();

        if (s1 == s2) {
            System.out.println("Both the strings objects are same");
        }
        else {
            System.out.println("Both the strings objects are different");
        }
    }
}
