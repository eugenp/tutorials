package com.baeldung.firstword;

public class FirstWordGetter {

    public static void main(String[] args) {
        String input = "Roberto \"I wish you a bug-free day\"";
        System.out.println("Using split: " + getFirstWordUsingSplit(input));
        System.out.println("Using subString: " + getFirstWordUsingSubString(input));
    }

    public static String getFirstWordUsingSubString(String input) {
        int index = input.contains(" ") ? input.indexOf(" ") : 0;
        return input.substring(0, index);
    }

    public static String getFirstWordUsingSplit(String input) {
        String[] tokens = input.split(" ", 2);
        return tokens[0];
    }
}
