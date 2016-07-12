package com.baeldung.mutation.test;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import org.junit.Test;

import com.baeldung.testing.mutation.Palindrome;

public class TestPalindrome {

    @Test
	public void acceptsPalindrome() {
	    Palindrome palindromeTester = new Palindrome();
	    assertTrue(palindromeTester.isPalindrome("noon"));
    }
    
    @Test
    public void rejectsNonPalindrome(){
    	Palindrome palindromeTester = new Palindrome();
    	assertFalse(palindromeTester.isPalindrome("box"));
    }
    
    @Test
    public void rejectsNearPalindrome(){
    	Palindrome palindromeTester = new Palindrome();
    	assertFalse(palindromeTester.isPalindrome("neon"));
    }
}
