package com.baeldung.mutation;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import org.junit.Test;

import com.baeldung.mutation.Palindrome;

public class PalindromeUnitTest {
    @Test
    public void whenEmptyString_thanAccept() {
        Palindrome palindromeTester = new Palindrome();
        assertTrue(palindromeTester.isPalindrome("noon"));
    }

    @Test
	public void whenPalindrom_thanAccept() {
	    Palindrome palindromeTester = new Palindrome();
	    assertTrue(palindromeTester.isPalindrome("noon"));
    }
    
    @Test
    public void whenNotPalindrom_thanReject(){
    	Palindrome palindromeTester = new Palindrome();
    	assertFalse(palindromeTester.isPalindrome("box"));
    }
    
    @Test
    public void whenNearPalindrom_thanReject(){
    	Palindrome palindromeTester = new Palindrome();
    	assertFalse(palindromeTester.isPalindrome("neon"));
    }
}
