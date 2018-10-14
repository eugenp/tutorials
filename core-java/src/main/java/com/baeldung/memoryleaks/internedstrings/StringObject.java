package com.baeldung.memoryleaks.internedstrings;

public class StringObject {
    private static final String FILEPATH = "C:\\bigstring.txt";
    
    public void readString() {
        String s1 = ReadStringFromFileUtil.read(FILEPATH);
        String s2 = ReadStringFromFileUtil.read(FILEPATH);

        if (s1 == s2) {
            System.out.println("Both the strings objects are same");
        }
        else {
            System.out.println("Both the strings objects are different");
        }
    }
}
