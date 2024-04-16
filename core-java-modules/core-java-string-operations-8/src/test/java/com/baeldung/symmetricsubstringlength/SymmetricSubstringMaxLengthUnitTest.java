package com.baeldung.symmetricsubstringlength;

import org.junit.Test;

import static org.junit.Assert.assertEquals;

public class SymmetricSubstringMaxLengthUnitTest {
    String input = "abba";
    int expected = 4;

    static int findLongestSymmetricSubstringUsingSymmetricApproach(String str) {
        int maxLength = 1;

        for (int i = 0; i < str.length(); i++) {
            for (int j = i; j < str.length(); j++) {
                int flag = 1;
                for (int k = 0; k < (j - i + 1) / 2; k++) {
                    if (str.charAt(i + k) != str.charAt(j - k)) {
                        flag = 0;
                        break;
                    }
                }
                if (flag != 0 && (j - i + 1) > maxLength) {
                    maxLength = j - i + 1;
                }
            }
        }
        return maxLength;
    }

    @Test
    public void givenString_whenUsingBruteForce_thenFindLongestSymmetricSubstring() {
        assertEquals(expected, findLongestSymmetricSubstringUsingBruteForce(input));
    }

    @Test
    public void givenString_whenUsingSymmetricSubstring_thenFindLongestSymmetricSubstring() {
        assertEquals(expected, findLongestSymmetricSubstringUsingSymmetricApproach(input));
    }

    private int findLongestSymmetricSubstringUsingBruteForce(String str) {
        if (str == null || str.length() == 0) {
            return 0;
        }

        int maxLength = 0;

        for (int i = 0; i < str.length(); i++) {
            for (int j = i + 1; j <= str.length(); j++) {
                String substring = str.substring(i, j);
                if (isPalindrome(substring) && substring.length() > maxLength) {
                    maxLength = substring.length();
                }
            }
        }

        return maxLength;
    }

    private boolean isPalindrome(String s) {
        int left = 0;
        int right = s.length() - 1;
        while (left < right) {
            if (s.charAt(left) != s.charAt(right)) {
                return false;
            }
            left++;
            right--;
        }
        return true;
    }
}
