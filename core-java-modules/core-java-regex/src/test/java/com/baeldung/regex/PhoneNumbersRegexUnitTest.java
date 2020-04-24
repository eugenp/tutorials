package com.baeldung.regex;

import static org.junit.Assert.*;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.junit.Test;

public class PhoneNumbersRegexUnitTest {

    @Test
    public void whenMatchesTenDigitsNumber_thenCorrect() {
        Pattern pattern = Pattern.compile("^\\d{10}$");
        Matcher matcher = pattern.matcher("1234567890");
        assertTrue(matcher.matches());
    }
    
    @Test
    public void whenMatchesTenDigitsNumberWhitespacesHyphen_thenCorrect() {
        Pattern pattern = Pattern.compile("^(\\d{3}[- ]?){2}\\d{4}$");
        Matcher matcher = pattern.matcher("123 456 7890");
        assertTrue(matcher.matches());
    }
    
    @Test
    public void whenMatchesTenDigitsNumberParenthesis_thenCorrect() {
        Pattern pattern = Pattern.compile("^\\(?\\d{3}\\)?[- ]?\\d{3}[- ]?\\d{4}$");
        Matcher matcher = pattern.matcher("(123)-456-7890");
        assertTrue(matcher.matches());
    }
    
    @Test
    public void whenMatchesTenDigitsNumberPrefix_thenCorrect() {
        Pattern pattern = Pattern.compile("^(\\+\\d{1,3}( )?)?\\(?\\d{3}\\)?[- ]?\\d{3}[- ]?\\d{4}$");
        Matcher matcher = pattern.matcher("+111 (123)-456-7890");
        assertTrue(matcher.matches());
    }
    
    @Test
    public void whenMatchesPhoneNumber_thenCorrect() {
        String patterns = "^(\\+\\d{1,3}( )?)?\\(?\\d{3}\\)?[- ]?\\d{3}[- ]?\\d{4}$" + 
                         "|^(\\+\\d{1,3}( )?)?(\\d{3}[ ]?){2}\\d{3}$" + 
                         "|^(\\+\\d{1,3}( )?)?(\\d{3}[ ]?)(\\d{2}[ ]?){2}\\d{2}$";
        
        String[] validPhoneNumbers = {"1234567890","123 456 7890", "(123)-456-7890", "+111 (123)-456-7890",
                                      "123 456 789", "+111 123 456 789", "123 45 67 89", "+111 123 45 67 89"};
        
        Pattern pattern = Pattern.compile(patterns);
        for(String phoneNumber : validPhoneNumbers) {
            Matcher matcher = pattern.matcher(phoneNumber);
            assertTrue(matcher.matches());
        }
    }
}
