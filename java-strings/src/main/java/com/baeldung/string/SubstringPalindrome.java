package com.baeldung.string;

import java.util.HashSet;
import java.util.Set;

public class SubstringPalindrome {

    public Set<String> isPalindromeUsingCenter(String input) {
        final Set<String> palindromes = new HashSet<>();
        if (input == null || input.isEmpty()) {
            return palindromes;
        }
        if (input.length() == 1) {
            palindromes.add(input);
            return palindromes;
        }
        for (int i = 0; i < input.length(); i++) {
            findPalindromes(palindromes, input, i, i + 1);
            findPalindromes(palindromes, input, i, i);
        }
        return palindromes;
    }

    private void findPalindromes(final Set<String> result, final String input, int low, int high) {
        while (low >= 0 && high < input.length() && input.charAt(low) == input.charAt(high)) {
            result.add(input.substring(low, high + 1));
            low--;
            high++;
        }
    }

    public Set<String> isPalindromeUsingSubstring(String input) {
        Set<String> palindromes = new HashSet<>();
        if (input == null || input.isEmpty()) {
            return palindromes;
        }
        if (input.length() == 1) {
            palindromes.add(input);
            return palindromes;
        }
        for (int i = 0; i < input.length(); i++) {
            for (int j = i + 1; j <= input.length(); j++)
                if (isPalindrome(input.substring(i, j))) {
                    palindromes.add(input.substring(i, j));
                }
        }
        return palindromes;
    }

    private boolean isPalindrome(String input) {
        StringBuilder plain = new StringBuilder(input);
        StringBuilder reverse = plain.reverse();
        return (reverse.toString()).equals(input);
    }

    public Set<String> isPalindromeUsingManachersAlgo(String input) {
        Set<String> palindromes = new HashSet<>();
        String formattedInput = "@" + input + "#";
        char inputCharArr[] = formattedInput.toCharArray();
        int rp;
        int radius[][] = new int[2][input.length() + 1];
        for (int j = 0; j <= 1; j++) {
            radius[j][0] = rp = 0;
            int i = 1;
            while (i <= input.length()) {
                palindromes.add(Character.toString(inputCharArr[i]));
                while (inputCharArr[i - rp - 1] == inputCharArr[i + j + rp])
                    rp++;
                radius[j][i] = rp;
                int k = 1;
                while ((radius[j][i - k] != rp - k) && (k < rp)) {
                    radius[j][i + k] = Math.min(radius[j][i - k], rp - k);
                    k++;
                }
                rp = Math.max(rp - k, 0);
                i += k;
            }
        }
        for (int i = 1; i <= input.length(); i++) {
            for (int j = 0; j <= 1; j++) {
                for (rp = radius[j][i]; rp > 0; rp--) {
                    palindromes.add(input.substring(i - rp - 1, (2 * rp + j) + (i - rp - 1)));
                }
            }
        }
        return palindromes;
    }
}
