package com.baeldung.string.interview;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.Test;

public class StringPalindromeUnitTest {
    
    public boolean isPalindrome(String text) {
        int forward = 0;
        int backward = text.length() - 1;
        while (backward > forward) {
            char forwardChar = text.charAt(forward++);
            char backwardChar = text.charAt(backward--);
            if (forwardChar != backwardChar)
                return false;
        }
        return true;
    }
    
    @Test
    public void givenIsPalindromeMethod_whenCheckingString_thenFindIfPalindrome() {
        assertThat(isPalindrome("madam")).isTrue();
        assertThat(isPalindrome("radar")).isTrue();
        assertThat(isPalindrome("level")).isTrue();

        assertThat(isPalindrome("baeldung")).isFalse();
    }
}
