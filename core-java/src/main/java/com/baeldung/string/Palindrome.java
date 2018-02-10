package com.baeldung.string;

public class Palindrome {

    public boolean isPalindrome(String text) {
        text = text.replaceAll("\\s+", "")
            .toLowerCase();
        int length = text.length();
        int forward = 0;
        int backward = length - 1;
        boolean palindrome = true;
        while (backward > forward) {
            char forwardChar = text.charAt(forward++);
            char backwardChar = text.charAt(backward--);
            if (forwardChar != backwardChar)
                return false;
        }
        return palindrome;
    }

    public boolean isPalindromeReverseTheString(String text) {
        String reverse = "";
        text = text.replaceAll("\\s+", "").toLowerCase();
        char[] plain = text.toCharArray();
        for (int i = plain.length - 1; i >= 0; i--)
            reverse += plain[i];
        return reverse.equals(text);
    }

    public boolean isPalindromeUsingStringBuilder(String text) {
        StringBuilder plain = new StringBuilder(text);
        StringBuilder reverse = plain.reverse();
        return reverse.equals(plain);
    }

    public boolean isPalindromeUsingStringBuffer(String text) {
        StringBuffer plain = new StringBuffer(text);
        StringBuffer reverse = plain.reverse();
        return reverse.equals(plain);
    }

    public boolean isPalindromeRecursive(String text, int forward, int backward) {
        if (forward == backward)
            return true;
        if ((text.charAt(forward)) != (text.charAt(backward)))
            return false;
        if (forward < backward + 1) {
            return isPalindromeRecursive(text, forward + 1, backward - 1);
        }

        return true;
    }
}
