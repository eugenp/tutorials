package com.baeldung.string;

public class Palindrome {

    public boolean isPalindrome(String text) {
        text = text.replaceAll("\\s+", "").toLowerCase();
        int length = text.length();
        int forward = 0;
        int backward = length - 1;
        boolean palindrome = true;
        while ((backward > forward)?(palindrome=(text.charAt(forward++) == text.charAt(backward--))):false);
        return palindrome;
    }
}
